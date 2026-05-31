package com.olympusgate.infrastructure.adapter

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class OpenAIAdapterTest {
    @Test
    fun `should call OpenAI API successfully`() {
        val adapter = OpenAIAdapter("test-api-key")
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
}
