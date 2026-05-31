package com.olympusgate.domain.service

import com.olympusgate.domain.model.ConditionType
import com.olympusgate.domain.model.Context
import com.olympusgate.domain.model.ModelConfiguration
import com.olympusgate.domain.model.Operator
import com.olympusgate.domain.model.Options
import com.olympusgate.domain.model.Priority
import com.olympusgate.domain.model.Request
import com.olympusgate.domain.model.ReturnMode
import com.olympusgate.domain.model.SelectionCondition
import com.olympusgate.domain.model.SelectionRule
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@Suppress("LongMethod")
class ModelSelectionEngineTest {
    @Test
    fun `should select model based on token count rule`() {
        val requestAnalyzer = RequestAnalyzer()
        val engine = ModelSelectionEngine(requestAnalyzer)
        val request =
            Request(
                prompt = "Hello, how are you?",
                context = Context(type = "text", content = emptyMap<String, Any>(), metadata = emptyMap()),
                options =
                    Options(
                        maxBudget = null,
                        priority = Priority.MEDIUM,
                        requireStreaming = false,
                        returnMode = ReturnMode.RESPONSE,
                        preferredModel = null,
                        excludeModels = emptyList(),
                    ),
            )
        val models =
            listOf(
                ModelConfiguration(
                    id = "model-1",
                    name = "GPT-3.5",
                    provider = "openai",
                    costPer1kTokens = 0.002,
                    maxTokens = 4096,
                    capabilities = listOf("chat"),
                    enabled = true,
                ),
                ModelConfiguration(
                    id = "model-2",
                    name = "GPT-4",
                    provider = "openai",
                    costPer1kTokens = 0.03,
                    maxTokens = 8192,
                    capabilities = listOf("chat", "code"),
                    enabled = true,
                ),
            )
        val rules =
            listOf(
                SelectionRule(
                    id = "rule-1",
                    name = "Low token rule",
                    priority = 1,
                    condition =
                        SelectionCondition(
                            type = ConditionType.TOKEN_COUNT,
                            operator = Operator.LESS_THAN,
                            value = "2000",
                        ),
                    targetModelId = "model-1",
                    enabled = true,
                ),
            )

        val selectedModel = engine.selectModel(request, models, rules)
        assertNotNull(selectedModel)
        assertEquals("model-1", selectedModel.id)
    }

    @Test
    fun `should select model based on priority rule`() {
        val requestAnalyzer = RequestAnalyzer()
        val engine = ModelSelectionEngine(requestAnalyzer)
        val request =
            Request(
                prompt = "Hello, how are you?",
                context = Context(type = "text", content = emptyMap<String, Any>(), metadata = emptyMap()),
                options =
                    Options(
                        maxBudget = null,
                        priority = Priority.MEDIUM,
                        requireStreaming = false,
                        returnMode = ReturnMode.RESPONSE,
                        preferredModel = null,
                        excludeModels = emptyList(),
                    ),
            )
        val models =
            listOf(
                ModelConfiguration(
                    id = "model-1",
                    name = "GPT-3.5",
                    provider = "openai",
                    costPer1kTokens = 0.002,
                    maxTokens = 4096,
                    capabilities = listOf("chat"),
                    enabled = true,
                ),
                ModelConfiguration(
                    id = "model-2",
                    name = "GPT-4",
                    provider = "openai",
                    costPer1kTokens = 0.03,
                    maxTokens = 8192,
                    capabilities = listOf("chat", "code"),
                    enabled = true,
                ),
            )
        val rules =
            listOf(
                SelectionRule(
                    id = "rule-1",
                    name = "Low priority rule",
                    priority = 2,
                    condition =
                        SelectionCondition(
                            type = ConditionType.TOKEN_COUNT,
                            operator = Operator.LESS_THAN,
                            value = "2000",
                        ),
                    targetModelId = "model-1",
                    enabled = true,
                ),
                SelectionRule(
                    id = "rule-2",
                    name = "High priority rule",
                    priority = 1,
                    condition =
                        SelectionCondition(
                            type = ConditionType.TOKEN_COUNT,
                            operator = Operator.LESS_THAN,
                            value = "2000",
                        ),
                    targetModelId = "model-2",
                    enabled = true,
                ),
            )

        val selectedModel = engine.selectModel(request, models, rules)
        assertNotNull(selectedModel)
        assertEquals("model-2", selectedModel.id)
    }

    @Test
    fun `should respect budget constraints`() {
        val requestAnalyzer = RequestAnalyzer()
        val engine = ModelSelectionEngine(requestAnalyzer)
        val request =
            Request(
                prompt = "Hello, how are you?",
                context = Context(type = "text", content = emptyMap<String, Any>(), metadata = emptyMap()),
                options =
                    Options(
                        maxBudget = 0.00001,
                        priority = Priority.MEDIUM,
                        requireStreaming = false,
                        returnMode = ReturnMode.RESPONSE,
                        preferredModel = null,
                        excludeModels = emptyList(),
                    ),
            )
        val models =
            listOf(
                ModelConfiguration(
                    id = "model-1",
                    name = "GPT-3.5",
                    provider = "openai",
                    costPer1kTokens = 0.002,
                    maxTokens = 4096,
                    capabilities = listOf("chat"),
                    enabled = true,
                ),
                ModelConfiguration(
                    id = "model-2",
                    name = "GPT-4",
                    provider = "openai",
                    costPer1kTokens = 0.03,
                    maxTokens = 8192,
                    capabilities = listOf("chat", "code"),
                    enabled = true,
                ),
            )
        val rules =
            listOf(
                SelectionRule(
                    id = "rule-1",
                    name = "Budget rule",
                    priority = 1,
                    condition =
                        SelectionCondition(
                            type = ConditionType.TOKEN_COUNT,
                            operator = Operator.LESS_THAN,
                            value = "2000",
                        ),
                    targetModelId = "model-2",
                    enabled = true,
                ),
            )

        val selectedModel = engine.selectModel(request, models, rules)
        assertNotNull(selectedModel)
        assertEquals("model-1", selectedModel.id)
    }

    @Test
    fun `should match required capabilities`() {
        val requestAnalyzer = RequestAnalyzer()
        val engine = ModelSelectionEngine(requestAnalyzer)
        val request =
            Request(
                prompt = "Write a function to sort an array",
                context =
                    Context(
                        type = "text",
                        content = emptyMap<String, Any>(),
                        metadata = mapOf("requiredCapabilities" to listOf("code")),
                    ),
                options =
                    Options(
                        maxBudget = null,
                        priority = Priority.MEDIUM,
                        requireStreaming = false,
                        returnMode = ReturnMode.RESPONSE,
                        preferredModel = null,
                        excludeModels = emptyList(),
                    ),
            )
        val models =
            listOf(
                ModelConfiguration(
                    id = "model-1",
                    name = "GPT-3.5",
                    provider = "openai",
                    costPer1kTokens = 0.002,
                    maxTokens = 4096,
                    capabilities = listOf("chat"),
                    enabled = true,
                ),
                ModelConfiguration(
                    id = "model-2",
                    name = "GPT-4",
                    provider = "openai",
                    costPer1kTokens = 0.03,
                    maxTokens = 8192,
                    capabilities = listOf("chat", "code"),
                    enabled = true,
                ),
            )
        val rules =
            listOf(
                SelectionRule(
                    id = "rule-1",
                    name = "Capability rule",
                    priority = 1,
                    condition =
                        SelectionCondition(
                            type = ConditionType.TOKEN_COUNT,
                            operator = Operator.LESS_THAN,
                            value = "2000",
                        ),
                    targetModelId = "model-1",
                    enabled = true,
                ),
            )

        val selectedModel = engine.selectModel(request, models, rules)
        assertNotNull(selectedModel)
        assertEquals("model-2", selectedModel.id)
    }

    @Test
    fun `should handle no matching rules`() {
        val requestAnalyzer = RequestAnalyzer()
        val engine = ModelSelectionEngine(requestAnalyzer)
        val request =
            Request(
                prompt = "Hello, how are you?",
                context = Context(type = "text", content = emptyMap<String, Any>(), metadata = emptyMap()),
                options =
                    Options(
                        maxBudget = null,
                        priority = Priority.MEDIUM,
                        requireStreaming = false,
                        returnMode = ReturnMode.RESPONSE,
                        preferredModel = null,
                        excludeModels = emptyList(),
                    ),
            )
        val models =
            listOf(
                ModelConfiguration(
                    id = "model-1",
                    name = "GPT-3.5",
                    provider = "openai",
                    costPer1kTokens = 0.002,
                    maxTokens = 4096,
                    capabilities = listOf("chat"),
                    enabled = true,
                ),
                ModelConfiguration(
                    id = "model-2",
                    name = "GPT-4",
                    provider = "openai",
                    costPer1kTokens = 0.03,
                    maxTokens = 8192,
                    capabilities = listOf("chat", "code"),
                    enabled = true,
                ),
            )
        val rules =
            listOf(
                SelectionRule(
                    id = "rule-1",
                    name = "High token rule",
                    priority = 1,
                    condition =
                        SelectionCondition(
                            type = ConditionType.TOKEN_COUNT,
                            operator = Operator.GREATER_THAN,
                            value = "10000",
                        ),
                    targetModelId = "model-2",
                    enabled = true,
                ),
            )

        val selectedModel = engine.selectModel(request, models, rules)
        assertNotNull(selectedModel)
        assertEquals("model-1", selectedModel.id)
    }

    @Test
    fun `should fallback to default model`() {
        val requestAnalyzer = RequestAnalyzer()
        val engine = ModelSelectionEngine(requestAnalyzer)
        val request =
            Request(
                prompt = "Hello, how are you?",
                context = Context(type = "text", content = emptyMap<String, Any>(), metadata = emptyMap()),
                options =
                    Options(
                        maxBudget = null,
                        priority = Priority.MEDIUM,
                        requireStreaming = false,
                        returnMode = ReturnMode.RESPONSE,
                        preferredModel = null,
                        excludeModels = emptyList(),
                    ),
            )
        val models =
            listOf(
                ModelConfiguration(
                    id = "model-1",
                    name = "GPT-3.5",
                    provider = "openai",
                    costPer1kTokens = 0.002,
                    maxTokens = 4096,
                    capabilities = listOf("chat"),
                    enabled = true,
                ),
            )
        val rules = emptyList<SelectionRule>()

        val selectedModel = engine.selectModel(request, models, rules)
        assertNotNull(selectedModel)
        assertEquals("model-1", selectedModel.id)
    }

    @Test
    fun `should exclude specific models`() {
        val requestAnalyzer = RequestAnalyzer()
        val engine = ModelSelectionEngine(requestAnalyzer)
        val request =
            Request(
                prompt = "Hello, how are you?",
                context = Context(type = "text", content = emptyMap<String, Any>(), metadata = emptyMap()),
                options =
                    Options(
                        maxBudget = null,
                        priority = Priority.MEDIUM,
                        requireStreaming = false,
                        returnMode = ReturnMode.RESPONSE,
                        preferredModel = null,
                        excludeModels = listOf("model-1"),
                    ),
            )
        val models =
            listOf(
                ModelConfiguration(
                    id = "model-1",
                    name = "GPT-3.5",
                    provider = "openai",
                    costPer1kTokens = 0.002,
                    maxTokens = 4096,
                    capabilities = listOf("chat"),
                    enabled = true,
                ),
                ModelConfiguration(
                    id = "model-2",
                    name = "GPT-4",
                    provider = "openai",
                    costPer1kTokens = 0.03,
                    maxTokens = 8192,
                    capabilities = listOf("chat", "code"),
                    enabled = true,
                ),
            )
        val rules =
            listOf(
                SelectionRule(
                    id = "rule-1",
                    name = "Low token rule",
                    priority = 1,
                    condition =
                        SelectionCondition(
                            type = ConditionType.TOKEN_COUNT,
                            operator = Operator.LESS_THAN,
                            value = "2000",
                        ),
                    targetModelId = "model-1",
                    enabled = true,
                ),
            )

        val selectedModel = engine.selectModel(request, models, rules)
        assertNotNull(selectedModel)
        assertEquals("model-2", selectedModel.id)
    }
}
