package com.olympusgate.domain.service

import com.olympusgate.domain.model.ConditionType
import com.olympusgate.domain.model.ModelConfiguration
import com.olympusgate.domain.model.Operator
import com.olympusgate.domain.model.Request
import com.olympusgate.domain.model.SelectionCondition
import com.olympusgate.domain.model.SelectionRule
import org.springframework.stereotype.Service

private const val TOKENS_PER_1K = 1000.0

@Suppress("TooManyFunctions")
@Service
class ModelSelectionEngine(
    private val requestAnalyzer: RequestAnalyzer,
) {
    fun selectModel(
        request: Request,
        models: List<ModelConfiguration>,
        rules: List<SelectionRule>,
    ): ModelConfiguration? {
        val availableModels = getAvailableModels(request, models) ?: return null

        val tokenCount = requestAnalyzer.estimateTokenCount(request.prompt)
        val taskType = requestAnalyzer.detectTaskType(request.prompt)
        val complexity = requestAnalyzer.analyzeComplexity(request.prompt)
        val context = RequestContext(tokenCount, taskType, complexity)

        val selectedByRule = selectByMatchingRules(availableModels, rules, context, request)
        return selectedByRule ?: selectByConstraints(availableModels, request)
    }

    private fun getAvailableModels(
        request: Request,
        models: List<ModelConfiguration>,
    ): List<ModelConfiguration>? {
        val enabledModels = models.filter { it.enabled }
        val availableModels =
            if (request.options.excludeModels.isNotEmpty()) {
                enabledModels.filter { it.id !in request.options.excludeModels }
            } else {
                enabledModels
            }

        return if (availableModels.isEmpty()) null else availableModels
    }

    private data class RequestContext(
        val tokenCount: Int,
        val taskType: String,
        val complexity: Int,
    )

    private fun selectByMatchingRules(
        availableModels: List<ModelConfiguration>,
        rules: List<SelectionRule>,
        context: RequestContext,
        request: Request,
    ): ModelConfiguration? {
        val matchingRules =
            rules
                .filter { rule -> ruleMatches(rule, context.tokenCount, context.taskType, context.complexity) }
                .sortedBy { it.priority }

        for (rule in matchingRules) {
            val targetModel = availableModels.find { it.id == rule.targetModelId }
            if (targetModel != null && modelMeetsConstraints(targetModel, request)) {
                return targetModel
            }
        }

        return null
    }

    private fun selectByConstraints(
        availableModels: List<ModelConfiguration>,
        request: Request,
    ): ModelConfiguration? {
        val modelsMeetingConstraints = availableModels.filter { modelMeetsConstraints(it, request) }
        if (modelsMeetingConstraints.isNotEmpty()) {
            return modelsMeetingConstraints.minByOrNull { it.costPer1kTokens }
        }

        return availableModels.firstOrNull()
    }

    private fun ruleMatches(
        rule: SelectionRule,
        tokenCount: Int,
        taskType: String,
        complexity: Int,
    ): Boolean {
        return conditionMatches(rule.condition, tokenCount, taskType, complexity)
    }

    private fun conditionMatches(
        condition: SelectionCondition,
        tokenCount: Int,
        taskType: String,
        complexity: Int,
    ): Boolean {
        val actualValue =
            when (condition.type) {
                ConditionType.TOKEN_COUNT -> tokenCount
                ConditionType.TASK_TYPE -> taskType
                ConditionType.COMPLEXITY -> complexity
                else -> return false
            }

        return evaluateOperator(condition.operator, actualValue, condition.value)
    }

    private fun evaluateOperator(
        operator: Operator,
        actualValue: Any,
        expectedValue: String,
    ): Boolean {
        return when (operator) {
            Operator.EQUALS -> actualValue.toString() == expectedValue
            Operator.NOT_EQUALS -> actualValue.toString() != expectedValue
            Operator.GREATER_THAN ->
                compareNumeric(
                    actualValue,
                    expectedValue,
                ) { a, b -> a > b }
            Operator.LESS_THAN ->
                compareNumeric(
                    actualValue,
                    expectedValue,
                ) { a, b -> a < b }
            Operator.GREATER_THAN_OR_EQUALS ->
                compareNumeric(
                    actualValue,
                    expectedValue,
                ) { a, b -> a >= b }
            Operator.LESS_THAN_OR_EQUALS ->
                compareNumeric(
                    actualValue,
                    expectedValue,
                ) { a, b -> a <= b }
            Operator.CONTAINS ->
                compareString(
                    actualValue,
                    expectedValue,
                ) { a, b -> a.contains(b, ignoreCase = true) }
            Operator.NOT_CONTAINS ->
                compareString(
                    actualValue,
                    expectedValue,
                ) { a, b -> !a.contains(b, ignoreCase = true) }
        }
    }

    private fun compareNumeric(
        actualValue: Any,
        expectedValue: String,
        comparison: (Int, Int) -> Boolean,
    ): Boolean {
        return if (actualValue is Int) {
            val expected = expectedValue.toIntOrNull()
            expected != null && comparison(actualValue, expected)
        } else {
            false
        }
    }

    private fun compareString(
        actualValue: Any,
        expectedValue: String,
        comparison: (String, String) -> Boolean,
    ): Boolean {
        if (actualValue !is String) return false
        return comparison(actualValue, expectedValue)
    }

    private fun modelMeetsConstraints(
        model: ModelConfiguration,
        request: Request,
    ): Boolean {
        if (!hasRequiredCapabilities(model, request)) {
            return false
        }

        return !exceedsBudget(model, request)
    }

    private fun hasRequiredCapabilities(
        model: ModelConfiguration,
        request: Request,
    ): Boolean {
        val requiredCapabilities =
            request.context.metadata["requiredCapabilities"] as? List<String>
        if (requiredCapabilities.isNullOrEmpty()) {
            return true
        }

        return requiredCapabilities.all { required ->
            model.capabilities.any { it.equals(required, ignoreCase = true) }
        }
    }

    private fun exceedsBudget(
        model: ModelConfiguration,
        request: Request,
    ): Boolean {
        val maxBudget = request.options.maxBudget ?: return false
        val estimatedCost =
            (requestAnalyzer.estimateTokenCount(request.prompt) / TOKENS_PER_1K) * model.costPer1kTokens
        return estimatedCost > maxBudget
    }
}
