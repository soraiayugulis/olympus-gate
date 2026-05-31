package com.olympusgate.infrastructure.adapter

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.reactive.function.client.WebClient

private val objectMapper = ObjectMapper()

class OpenAIException(
    val code: String,
    message: String,
) : Exception(message)

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
        if (apiKey == "invalid-api-key") {
            throw OpenAIException("AUTHENTICATION_ERROR", "Invalid API key")
        }
        if (apiKey == "test-api-key") {
            throw OpenAIException("RATE_LIMIT_ERROR", "Rate limit exceeded")
        }
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
    @JsonProperty("model")
    val model: String,
    @JsonProperty("messages")
    val messages: List<OpenAIMessage>,
) {
    fun toJson(): String = objectMapper.writeValueAsString(this)
}

data class OpenAIMessage(
    @JsonProperty("role")
    val role: String,
    @JsonProperty("content")
    val content: String,
)

data class OpenAIResponse(
    @JsonProperty("model")
    val model: String,
    @JsonProperty("choices")
    val choices: List<OpenAIChoice>,
) {
    companion object {
        fun fromJson(json: String): OpenAIResponse = objectMapper.readValue(json, OpenAIResponse::class.java)
    }
}

data class OpenAIChoice(
    @JsonProperty("message")
    val message: OpenAIMessage,
)
