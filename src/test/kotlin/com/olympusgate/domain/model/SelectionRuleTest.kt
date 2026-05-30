package com.olympusgate.domain.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SelectionRuleTest {

    @Test
    fun `should accept valid input`() {
        val rule = SelectionRule(
            id = "rule-1",
            name = "Simple tasks to GPT-3.5",
            priority = 1,
            condition = SelectionCondition(
                type = ConditionType.TOKEN_COUNT,
                operator = Operator.LESS_THAN,
                value = "1000"
            ),
            targetModelId = "gpt-3.5-turbo",
            enabled = true
        )

        assertEquals("rule-1", rule.id)
        assertEquals("Simple tasks to GPT-3.5", rule.name)
        assertEquals(1, rule.priority)
        assertEquals(ConditionType.TOKEN_COUNT, rule.condition.type)
        assertEquals("gpt-3.5-turbo", rule.targetModelId)
        assertTrue(rule.enabled)
    }

    @Test
    fun `should reject negative priority`() {
        val exception = assertThrows<IllegalArgumentException> {
            SelectionRule(
                id = "rule-1",
                name = "Simple tasks to GPT-3.5",
                priority = -1,
                condition = SelectionCondition(
                    type = ConditionType.TOKEN_COUNT,
                    operator = Operator.LESS_THAN,
                    value = "1000"
                ),
                targetModelId = "gpt-3.5-turbo",
                enabled = true
            )
        }

        assertTrue(exception.message!!.contains("Priority"))
    }

    @Test
    fun `should require non-empty target model id`() {
        val exception = assertThrows<IllegalArgumentException> {
            SelectionRule(
                id = "rule-1",
                name = "Simple tasks to GPT-3.5",
                priority = 1,
                condition = SelectionCondition(
                    type = ConditionType.TOKEN_COUNT,
                    operator = Operator.LESS_THAN,
                    value = "1000"
                ),
                targetModelId = "",
                enabled = true
            )
        }

        assertTrue(exception.message!!.contains("Target model"))
    }
}
