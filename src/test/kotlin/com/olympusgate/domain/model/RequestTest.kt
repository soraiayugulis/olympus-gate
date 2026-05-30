package com.olympusgate.domain.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RequestTest {
    @Test
    fun `should accept valid input`() {
        val request =
            Request(
                prompt = "Write a function to sort an array",
                context =
                    Context(
                        type = "code",
                        content = "Python code needed",
                        metadata = mapOf("language" to "python"),
                    ),
                options =
                    Options(
                        maxBudget = 0.05,
                        priority = Priority.MEDIUM,
                        requireStreaming = false,
                        returnMode = ReturnMode.RESPONSE,
                        preferredModel = null,
                        excludeModels = emptyList(),
                    ),
            )

        assertEquals("Write a function to sort an array", request.prompt)
        assertEquals("code", request.context.type)
        assertEquals(Priority.MEDIUM, request.options.priority)
        assertEquals(ReturnMode.RESPONSE, request.options.returnMode)
    }

    @Test
    fun `should reject empty prompt`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Request(
                    prompt = "",
                    context =
                        Context(
                            type = "code",
                            content = "Python code needed",
                            metadata = emptyMap(),
                        ),
                    options =
                        Options(
                            maxBudget = null,
                            priority = Priority.LOW,
                            requireStreaming = false,
                            returnMode = ReturnMode.RESPONSE,
                            preferredModel = null,
                            excludeModels = emptyList(),
                        ),
                )
            }

        assertTrue(exception.message!!.contains("Prompt"))
    }

    @Test
    fun `should reject negative budget`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Request(
                    prompt = "Write a function",
                    context =
                        Context(
                            type = "code",
                            content = "Python code needed",
                            metadata = emptyMap(),
                        ),
                    options =
                        Options(
                            maxBudget = -0.01,
                            priority = Priority.LOW,
                            requireStreaming = false,
                            returnMode = ReturnMode.RESPONSE,
                            preferredModel = null,
                            excludeModels = emptyList(),
                        ),
                )
            }

        assertTrue(exception.message!!.contains("Budget"))
    }
}
