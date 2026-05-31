package com.olympusgate.infrastructure.persistence

import com.olympusgate.domain.model.SelectionRule
import com.olympusgate.infrastructure.persistence.entity.SelectionRuleEntity
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SelectionRuleRepositoryTest {
    @Test
    fun `should convert domain model to entity`() {
        val rule =
            SelectionRule(
                id = "rule-1",
                name = "Code Tasks Rule",
                priority = 1,
                condition =
                    com.olympusgate.domain.model.SelectionCondition(
                        type = com.olympusgate.domain.model.ConditionType.TASK_TYPE,
                        operator = com.olympusgate.domain.model.Operator.EQUALS,
                        value = "code",
                    ),
                targetModelId = "gpt-4",
                enabled = true,
            )

        val entity =
            SelectionRuleEntity(
                id = rule.id,
                name = rule.name,
                priority = rule.priority,
                conditionType = rule.condition.type.name,
                conditionOperator = rule.condition.operator.name,
                conditionValue = rule.condition.value,
                targetModelId = rule.targetModelId,
                enabled = rule.enabled,
            )

        assertEquals("rule-1", entity.id)
        assertEquals("Code Tasks Rule", entity.name)
        assertEquals(1, entity.priority)
        assertEquals("TASK_TYPE", entity.conditionType)
        assertEquals("EQUALS", entity.conditionOperator)
        assertEquals("code", entity.conditionValue)
        assertEquals("gpt-4", entity.targetModelId)
        assertEquals(true, entity.enabled)
    }

    @Test
    fun `should convert entity to domain model`() {
        val entity =
            SelectionRuleEntity(
                id = "rule-1",
                name = "Code Tasks Rule",
                priority = 1,
                conditionType = "TASK_TYPE",
                conditionOperator = "EQUALS",
                conditionValue = "code",
                targetModelId = "gpt-4",
                enabled = true,
            )

        val rule =
            SelectionRule(
                id = entity.id,
                name = entity.name,
                priority = entity.priority,
                condition =
                    com.olympusgate.domain.model.SelectionCondition(
                        type =
                            com.olympusgate.domain.model.ConditionType.valueOf(
                                entity.conditionType,
                            ),
                        operator =
                            com.olympusgate.domain.model.Operator.valueOf(
                                entity.conditionOperator,
                            ),
                        value = entity.conditionValue,
                    ),
                targetModelId = entity.targetModelId,
                enabled = entity.enabled,
            )

        assertEquals("rule-1", rule.id)
        assertEquals("Code Tasks Rule", rule.name)
        assertEquals(1, rule.priority)
        assertEquals(
            com.olympusgate.domain.model.ConditionType.TASK_TYPE,
            rule.condition.type,
        )
        assertEquals(
            com.olympusgate.domain.model.Operator.EQUALS,
            rule.condition.operator,
        )
        assertEquals("code", rule.condition.value)
        assertEquals("gpt-4", rule.targetModelId)
        assertEquals(true, rule.enabled)
    }
}
