package com.olympusgate.domain.model

data class Request(
    val prompt: String,
    val context: Context,
    val options: Options
) {
    init {
        require(prompt.isNotBlank()) { "Prompt cannot be blank" }
    }
}

data class Context(
    val type: String,
    val content: Any,
    val metadata: Map<String, Any>
)

data class Options(
    val maxBudget: Double?,
    val priority: Priority,
    val requireStreaming: Boolean,
    val returnMode: ReturnMode,
    val preferredModel: String?,
    val excludeModels: List<String>
) {
    init {
        if (maxBudget != null) {
            require(maxBudget >= 0) { "Budget must be non-negative" }
        }
    }
}

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}

enum class ReturnMode {
    MODEL_ONLY,
    RESPONSE
}
