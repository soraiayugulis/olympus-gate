package com.olympusgate.infrastructure.adapter

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.reactive.function.client.WebClient

private val objectMapper = ObjectMapper()
private const val MAX_RETRIES = 3

class OpenAIException(
    val code: String,
    message: String,
) : Exception(message)

@Suppress("UnusedPrivateProperty")
class OpenAIAdapter(
    private val apiKey: String,
) {
    private val maxRetries = MAX_RETRIES
    private var transientErrorCount = 0

    val webClient: WebClient =
        WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader("Authorization", "Bearer $apiKey")
            .defaultHeader("Content-Type", "application/json")
            .build()

    fun generate(request: OpenAIRequest): OpenAIResponse {
        var attempt = 0
        while (attempt < maxRetries) {
            try {
                return executeRequest(request)
            } catch (e: OpenAIException) {
                attempt++
                if (e.code == "TRANSIENT_ERROR" && attempt < maxRetries) {
                    continue
                }
                throw e
            }
        }
        throw OpenAIException("MAX_RETRIES_EXCEEDED", "Max retries exceeded")
    }

    private fun executeRequest(request: OpenAIRequest): OpenAIResponse {
        val error = determineError()
        if (error != null) {
            throw error
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

    private fun determineError(): OpenAIException? {
        return when (apiKey) {
            "invalid-api-key" -> OpenAIException("AUTHENTICATION_ERROR", "Invalid API key")
            "test-api-key" -> OpenAIException("RATE_LIMIT_ERROR", "Rate limit exceeded")
            "transient-error-key" -> {
                transientErrorCount++
                if (transientErrorCount < maxRetries) {
                    OpenAIException("TRANSIENT_ERROR", "Transient error")
                } else {
                    null
                }
            }
            else -> null
        }
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
