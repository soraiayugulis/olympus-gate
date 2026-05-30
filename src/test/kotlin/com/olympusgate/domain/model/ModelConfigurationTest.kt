package com.olympusgate.domain.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ModelConfigurationTest {
    @Test
    fun `should accept valid input`() {
        val config =
            ModelConfiguration(
                id = "gpt-4",
                name = "GPT-4",
                provider = "openai",
                costPer1kTokens = 0.03,
                maxTokens = 8192,
                capabilities = listOf("code", "reasoning", "qa"),
                enabled = true,
            )

        assertEquals("gpt-4", config.id)
        assertEquals("GPT-4", config.name)
        assertEquals("openai", config.provider)
        assertEquals(0.03, config.costPer1kTokens)
        assertEquals(8192, config.maxTokens)
        assertEquals(listOf("code", "reasoning", "qa"), config.capabilities)
        assertTrue(config.enabled)
    }

    @Test
    fun `should reject negative cost values`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                ModelConfiguration(
                    id = "gpt-4",
                    name = "GPT-4",
                    provider = "openai",
                    costPer1kTokens = -0.03,
                    maxTokens = 8192,
                    capabilities = listOf("code", "reasoning", "qa"),
                    enabled = true,
                )
            }

        assertTrue(exception.message!!.contains("non-negative"))
    }

    @Test
    fun `should reject zero max tokens`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                ModelConfiguration(
                    id = "gpt-4",
                    name = "GPT-4",
                    provider = "openai",
                    costPer1kTokens = 0.03,
                    maxTokens = 0,
                    capabilities = listOf("code", "reasoning", "qa"),
                    enabled = true,
                )
            }

        assertTrue(exception.message!!.contains("positive"))
    }

    @Test
    fun `should require non-empty capabilities list`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                ModelConfiguration(
                    id = "gpt-4",
                    name = "GPT-4",
                    provider = "openai",
                    costPer1kTokens = 0.03,
                    maxTokens = 8192,
                    capabilities = emptyList(),
                    enabled = true,
                )
            }

        assertTrue(exception.message!!.contains("empty"))
    }
}
