package com.olympusgate.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "selection_rules")
data class SelectionRuleEntity(
    @Id
    @Column(name = "id")
    val id: String,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "priority", nullable = false)
    val priority: Int,
    @Column(name = "condition_type", nullable = false)
    val conditionType: String,
    @Column(name = "condition_operator", nullable = false)
    val conditionOperator: String,
    @Column(name = "condition_value", nullable = false)
    val conditionValue: String,
    @Column(name = "target_model_id", nullable = false)
    val targetModelId: String,
    @Column(name = "enabled", nullable = false)
    val enabled: Boolean,
    @Column(name = "created_at")
    val createdAt: LocalDateTime? = null,
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime? = null,
)
