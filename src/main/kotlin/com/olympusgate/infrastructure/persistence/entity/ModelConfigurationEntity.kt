package com.olympusgate.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "model_configurations")
data class ModelConfigurationEntity(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "provider", nullable = false)
    val provider: String,

    @Column(name = "cost_per_1k_tokens", nullable = false)
    val costPer1kTokens: Double,

    @Column(name = "max_tokens", nullable = false)
    val maxTokens: Int,

    @Column(name = "capabilities", nullable = false)
    val capabilities: String,

    @Column(name = "enabled", nullable = false)
    val enabled: Boolean,

    @Column(name = "created_at")
    val createdAt: LocalDateTime? = null,

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime? = null,
)
