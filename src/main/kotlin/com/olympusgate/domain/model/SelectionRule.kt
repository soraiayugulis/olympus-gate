package com.olympusgate.domain.model

data class SelectionRule(
    val id: String,
    val name: String,
    val priority: Int,
    val condition: SelectionCondition,
    val targetModelId: String,
    val enabled: Boolean
) {
    init {
        require(priority >= 0) { "Priority must be non-negative" }
        require(targetModelId.isNotBlank()) { "Target model ID cannot be blank" }
    }
}

data class SelectionCondition(
    val type: ConditionType,
    val operator: Operator,
    val value: String
)

enum class ConditionType {
    TOKEN_COUNT,
    COMPLEXITY,
    TASK_TYPE,
    BUDGET,
    CAPABILITY
}

enum class Operator {
    EQUALS,
    NOT_EQUALS,
    LESS_THAN,
    GREATER_THAN,
    LESS_THAN_OR_EQUALS,
    GREATER_THAN_OR_EQUALS,
    CONTAINS,
    NOT_CONTAINS
}
