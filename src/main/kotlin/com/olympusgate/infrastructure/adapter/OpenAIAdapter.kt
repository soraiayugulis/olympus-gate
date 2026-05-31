package com.olympusgate.infrastructure.adapter

import org.springframework.web.reactive.function.client.WebClient

@Suppress("UnusedPrivateProperty")
class OpenAIAdapter(
    private val apiKey: String,
) {
    val webClient: WebClient =
        WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader("Authorization", "Bearer $apiKey")
            .defaultHeader("Content-Type", "application/json")
            .build()

    fun generate(request: OpenAIRequest): OpenAIResponse {
        return OpenAIResponse(
            model = request.model,
            choices =
                listOf(
                    OpenAIChoice(
                        message =
                            OpenAIMessage(
                                role = "assistant",
                                content = "Test response",
                            ),
                    ),
                ),
        )
    }
}

data class OpenAIRequest(
    val model: String,
    val messages: List<OpenAIMessage>,
)

data class OpenAIMessage(
    val role: String,
    val content: String,
)

data class OpenAIResponse(
    val model: String,
    val choices: List<OpenAIChoice>,
)

data class OpenAIChoice(
    val message: OpenAIMessage,
)
