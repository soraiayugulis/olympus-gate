package com.olympusgate.infrastructure.adapter

@Suppress("UnusedPrivateProperty")
class OpenAIAdapter(
    private val apiKey: String,
) {
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
