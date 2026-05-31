package com.olympusgate.infrastructure.persistence

import com.olympusgate.domain.model.ModelConfiguration
import com.olympusgate.infrastructure.persistence.entity.ModelConfigurationEntity
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ModelConfigurationRepositoryTest {
    @Test
    fun `should convert domain model to entity`() {
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

        val entity =
            ModelConfigurationEntity(
                id = config.id,
                name = config.name,
                provider = config.provider,
                costPer1kTokens = config.costPer1kTokens,
                maxTokens = config.maxTokens,
                capabilities = config.capabilities.joinToString(","),
                enabled = config.enabled,
            )

        assertEquals("gpt-4", entity.id)
        assertEquals("GPT-4", entity.name)
        assertEquals("code,reasoning,qa", entity.capabilities)
    }

    @Test
    fun `should convert entity to domain model`() {
        val entity =
            ModelConfigurationEntity(
                id = "gpt-4",
                name = "GPT-4",
                provider = "openai",
                costPer1kTokens = 0.03,
                maxTokens = 8192,
                capabilities = "code,reasoning,qa",
                enabled = true,
            )

        val config =
            ModelConfiguration(
                id = entity.id,
                name = entity.name,
                provider = entity.provider,
                costPer1kTokens = entity.costPer1kTokens,
                maxTokens = entity.maxTokens,
                capabilities = entity.capabilities.split(","),
                enabled = entity.enabled,
            )

        assertEquals("gpt-4", config.id)
        assertEquals(3, config.capabilities.size)
        assertTrue(config.capabilities.contains("code"))
    }
}
