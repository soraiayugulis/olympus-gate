package com.olympusgate.domain.model

data class Response(
    val content: String?,
    val modelInfo: ModelInfo,
    val routingMetadata: RoutingMetadata,
    val returnMode: ReturnMode
)

data class ModelInfo(
    val modelId: String,
    val provider: String,
    val estimatedCost: Double,
    val estimatedTokens: Int
) {
    init {
        require(estimatedCost >= 0) { "Estimated cost must be non-negative" }
        require(estimatedTokens >= 0) { "Estimated tokens must be non-negative" }
    }
}

data class RoutingMetadata(
    val selectedModelId: String,
    val selectionReason: String,
    val matchedRuleId: String?,
    val latencyMs: Long
) {
    init {
        require(selectedModelId.isNotBlank()) { "Selected model ID cannot be blank" }
        require(latencyMs >= 0) { "Latency must be non-negative" }
    }
}
