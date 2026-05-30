package com.olympusgate.domain.model

data class ModelConfiguration(
    val id: String,
    val name: String,
    val provider: String,
    val costPer1kTokens: Double,
    val maxTokens: Int,
    val capabilities: List<String>,
    val enabled: Boolean,
) {
    init {
        require(costPer1kTokens >= 0) { "Cost per 1k tokens must be non-negative" }
        require(maxTokens > 0) { "Max tokens must be positive" }
        require(capabilities.isNotEmpty()) { "Capabilities list cannot be empty" }
    }
}
