package com.olympusgate.infrastructure.persistence

import com.olympusgate.domain.model.ModelConfiguration
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ModelConfigurationRepositoryTest {

    @Test
    fun `should save model configuration`() {
        val repository = ModelConfigurationRepository()
        val config = ModelConfiguration(
            id = "gpt-4",
            name = "GPT-4",
            provider = "openai",
            costPer1kTokens = 0.03,
            maxTokens = 8192,
            capabilities = listOf("code", "reasoning", "qa"),
            enabled = true
        )

        repository.save(config)

        val saved = repository.findById("gpt-4")
        assertNotNull(saved)
        assertEquals("gpt-4", saved.id)
    }

    @Test
    fun `should find model by ID`() {
        val repository = ModelConfigurationRepository()
        val config = ModelConfiguration(
            id = "gpt-3.5-turbo",
            name = "GPT-3.5 Turbo",
            provider = "openai",
            costPer1kTokens = 0.002,
            maxTokens = 4096,
            capabilities = listOf("code", "qa"),
            enabled = true
        )

        repository.save(config)

        val found = repository.findById("gpt-3.5-turbo")
        assertNotNull(found)
        assertEquals("GPT-3.5 Turbo", found.name)
    }

    @Test
    fun `should find all enabled models`() {
        val repository = ModelConfigurationRepository()
        repository.save(
            ModelConfiguration(
                id = "gpt-4",
                name = "GPT-4",
                provider = "openai",
                costPer1kTokens = 0.03,
                maxTokens = 8192,
                capabilities = listOf("code"),
                enabled = true
            )
        )
        repository.save(
            ModelConfiguration(
                id = "gpt-3.5-turbo",
                name = "GPT-3.5 Turbo",
                provider = "openai",
                costPer1kTokens = 0.002,
                maxTokens = 4096,
                capabilities = listOf("qa"),
                enabled = false
            )
        )

        val enabledModels = repository.findAllEnabled()

        assertEquals(1, enabledModels.size)
        assertTrue(enabledModels.first().enabled)
    }

    @Test
    fun `should update model configuration`() {
        val repository = ModelConfigurationRepository()
        val config = ModelConfiguration(
            id = "gpt-4",
            name = "GPT-4",
            provider = "openai",
            costPer1kTokens = 0.03,
            maxTokens = 8192,
            capabilities = listOf("code"),
            enabled = true
        )

        repository.save(config)

        val updated = ModelConfiguration(
            id = "gpt-4",
            name = "GPT-4 Updated",
            provider = "openai",
            costPer1kTokens = 0.025,
            maxTokens = 8192,
            capabilities = listOf("code", "reasoning"),
            enabled = true
        )

        repository.save(updated)

        val found = repository.findById("gpt-4")
        assertNotNull(found)
        assertEquals("GPT-4 Updated", found.name)
        assertEquals(0.025, found.costPer1kTokens)
    }

    @Test
    fun `should delete model configuration`() {
        val repository = ModelConfigurationRepository()
        val config = ModelConfiguration(
            id = "gpt-4",
            name = "GPT-4",
            provider = "openai",
            costPer1kTokens = 0.03,
            maxTokens = 8192,
            capabilities = listOf("code"),
            enabled = true
        )

        repository.save(config)
        repository.deleteById("gpt-4")

        val found = repository.findById("gpt-4")
        assertEquals(null, found)
    }

    @Test
    fun `should find models by provider`() {
        val repository = ModelConfigurationRepository()
        repository.save(
            ModelConfiguration(
                id = "gpt-4",
                name = "GPT-4",
                provider = "openai",
                costPer1kTokens = 0.03,
                maxTokens = 8192,
                capabilities = listOf("code"),
                enabled = true
            )
        )
        repository.save(
            ModelConfiguration(
                id = "claude-3",
                name = "Claude 3",
                provider = "anthropic",
                costPer1kTokens = 0.015,
                maxTokens = 100000,
                capabilities = listOf("reasoning"),
                enabled = true
            )
        )

        val openaiModels = repository.findByProvider("openai")

        assertEquals(1, openaiModels.size)
        assertEquals("openai", openaiModels.first().provider)
    }
}
