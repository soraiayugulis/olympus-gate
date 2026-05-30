package com.olympusgate.domain.model

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ResponseTest {

    @Test
    fun `should accept valid input with full response`() {
        val response = Response(
            content = "Here is the sorted array function",
            modelInfo = ModelInfo(
                modelId = "gpt-4",
                provider = "openai",
                estimatedCost = 0.02,
                estimatedTokens = 500
            ),
            routingMetadata = RoutingMetadata(
                selectedModelId = "gpt-4",
                selectionReason = "High priority request",
                matchedRuleId = "rule-1",
                latencyMs = 1500
            ),
            returnMode = ReturnMode.RESPONSE
        )

        assertEquals("Here is the sorted array function", response.content)
        assertEquals("gpt-4", response.modelInfo.modelId)
        assertEquals("gpt-4", response.routingMetadata.selectedModelId)
        assertEquals(ReturnMode.RESPONSE, response.returnMode)
    }

    @Test
    fun `should accept valid input with model only`() {
        val response = Response(
            content = null,
            modelInfo = ModelInfo(
                modelId = "gpt-3.5-turbo",
                provider = "openai",
                estimatedCost = 0.005,
                estimatedTokens = 200
            ),
            routingMetadata = RoutingMetadata(
                selectedModelId = "gpt-3.5-turbo",
                selectionReason = "Simple task",
                matchedRuleId = "rule-2",
                latencyMs = 0
            ),
            returnMode = ReturnMode.MODEL_ONLY
        )

        assertEquals(null, response.content)
        assertEquals("gpt-3.5-turbo", response.modelInfo.modelId)
        assertEquals(ReturnMode.MODEL_ONLY, response.returnMode)
    }

    @Test
    fun `should serialize correctly`() {
        val response = Response(
            content = "Test response",
            modelInfo = ModelInfo(
                modelId = "gpt-4",
                provider = "openai",
                estimatedCost = 0.01,
                estimatedTokens = 100
            ),
            routingMetadata = RoutingMetadata(
                selectedModelId = "gpt-4",
                selectionReason = "Test",
                matchedRuleId = "rule-1",
                latencyMs = 100
            ),
            returnMode = ReturnMode.RESPONSE
        )

        assertNotNull(response.content)
        assertNotNull(response.modelInfo)
        assertNotNull(response.routingMetadata)
        assertTrue(response.modelInfo.estimatedCost >= 0)
        assertTrue(response.modelInfo.estimatedTokens > 0)
    }
}
