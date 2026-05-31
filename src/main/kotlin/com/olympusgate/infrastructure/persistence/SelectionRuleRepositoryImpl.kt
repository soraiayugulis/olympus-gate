package com.olympusgate.infrastructure.persistence

import com.olympusgate.domain.model.ConditionType
import com.olympusgate.domain.model.Operator
import com.olympusgate.domain.model.SelectionCondition
import com.olympusgate.domain.model.SelectionRule
import com.olympusgate.domain.repository.SelectionRuleRepository
import com.olympusgate.infrastructure.persistence.entity.SelectionRuleEntity
import org.springframework.stereotype.Repository

@Repository
class SelectionRuleRepositoryImpl(
    private val jpaRepository: SelectionRuleSpringDataRepository,
) : SelectionRuleRepository {
    override fun save(rule: SelectionRule): SelectionRule {
        val entity = toEntity(rule)
        val saved = jpaRepository.save(entity)
        return toDomain(saved)
    }

    override fun findById(id: String): SelectionRule? {
        return jpaRepository.findById(id).map { toDomain(it) }.orElse(null)
    }

    override fun findAll(): List<SelectionRule> {
        return jpaRepository.findAll().map { toDomain(it) }
    }

    override fun findAllEnabled(): List<SelectionRule> {
        return jpaRepository.findByEnabledTrue().map { toDomain(it) }
    }

    override fun findByPriority(priority: Int): List<SelectionRule> {
        return jpaRepository.findByPriority(priority).map { toDomain(it) }
    }

    override fun deleteById(id: String) {
        jpaRepository.deleteById(id)
    }

    private fun toEntity(rule: SelectionRule): SelectionRuleEntity {
        return SelectionRuleEntity(
            id = rule.id,
            name = rule.name,
            priority = rule.priority,
            conditionType = rule.condition.type.name,
            conditionOperator = rule.condition.operator.name,
            conditionValue = rule.condition.value,
            targetModelId = rule.targetModelId,
            enabled = rule.enabled,
        )
    }

    private fun toDomain(entity: SelectionRuleEntity): SelectionRule {
        return SelectionRule(
            id = entity.id,
            name = entity.name,
            priority = entity.priority,
            condition =
                SelectionCondition(
                    type =
                        ConditionType.valueOf(
                            entity.conditionType,
                        ),
                    operator =
                        Operator.valueOf(
                            entity.conditionOperator,
                        ),
                    value = entity.conditionValue,
                ),
            targetModelId = entity.targetModelId,
            enabled = entity.enabled,
        )
    }
}
