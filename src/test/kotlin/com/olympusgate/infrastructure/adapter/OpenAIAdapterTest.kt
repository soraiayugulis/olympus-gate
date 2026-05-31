package com.olympusgate.infrastructure.adapter

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

class OpenAIAdapterTest {
    @Test
    fun `should call OpenAI API successfully`() {
        val adapter = OpenAIAdapter("valid-api-key")
        val request =
            OpenAIRequest(
                model = "gpt-3.5-turbo",
                messages =
                    listOf(
                        OpenAIMessage(role = "user", content = "Hello, how are you?"),
                    ),
            )

        val response = adapter.generate(request)

        assertNotNull(response)
        assertEquals("gpt-3.5-turbo", response.model)
        assertNotNull(response.choices.first().message.content)
    }

    @Test
    fun `should configure WebClient with API key authentication`() {
        val adapter = OpenAIAdapter("test-api-key")
        assertNotNull(adapter.webClient)
    }

    @Test
    fun `should serialize request correctly`() {
        val request =
            OpenAIRequest(
                model = "gpt-3.5-turbo",
                messages =
                    listOf(
                        OpenAIMessage(role = "user", content = "Hello"),
                    ),
            )
        val json = request.toJson()
        assertNotNull(json)
        assertEquals("gpt-3.5-turbo", json.substringAfter("\"model\":\"").substringBefore("\""))
    }

    @Test
    fun `should deserialize response correctly`() {
        val json =
            """
            {
                "model": "gpt-3.5-turbo",
                "choices": [
                    {
                        "message": {
                            "role": "assistant",
                            "content": "Hello!"
                        }
                    }
                ]
            }
            """.trimIndent()
        val response = OpenAIResponse.fromJson(json)
        assertNotNull(response)
        assertEquals("gpt-3.5-turbo", response.model)
        assertEquals("Hello!", response.choices.first().message.content)
    }

    @Test
    fun `should handle authentication errors`() {
        val adapter = OpenAIAdapter("invalid-api-key")
        val request =
            OpenAIRequest(
                model = "gpt-3.5-turbo",
                messages =
                    listOf(
                        OpenAIMessage(role = "user", content = "Hello"),
                    ),
            )
        try {
            adapter.generate(request)
            fail("Should throw OpenAIException for authentication error")
        } catch (e: OpenAIException) {
            assertEquals("AUTHENTICATION_ERROR", e.code)
        }
    }

    @Test
    fun `should handle rate limit errors`() {
        val adapter = OpenAIAdapter("test-api-key")
        val request =
            OpenAIRequest(
                model = "gpt-3.5-turbo",
                messages =
                    listOf(
                        OpenAIMessage(role = "user", content = "Hello"),
                    ),
            )
        try {
            adapter.generate(request)
            fail("Should throw OpenAIException for rate limit error")
        } catch (e: OpenAIException) {
            assertEquals("RATE_LIMIT_ERROR", e.code)
        }
    }
}
