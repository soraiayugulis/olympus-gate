package com.olympusgate.domain.service

import org.springframework.stereotype.Service

@Service
class RequestAnalyzer {
    companion object {
        private const val TOKENS_PER_CHAR = 4
        private const val COMPLEXITY_BASE_DIVISOR = 100
        private const val MAX_COMPLEXITY = 100
    }

    fun estimateTokenCount(text: String): Int {
        if (text.isBlank()) {
            return 0
        }
        // Rough estimation: ~4 characters per token for English text
        // This is a simplified estimation; in production, use a proper tokenizer
        return (text.length / TOKENS_PER_CHAR).coerceAtLeast(1)
    }

    fun analyzeComplexity(text: String): Int {
        if (text.isBlank()) {
            return 0
        }

        var complexity = 0

        // Code complexity indicators
        val codePatterns = listOf(
            "fun ", "function ", "def ", "class ", "interface ",
            "if ", "else ", "for ", "while ", "when ", "match ",
            "flatMap", "fold", "reduce", "map", "filter",
            "{", "}", "(", ")", "[", "]",
        )

        // Reasoning complexity indicators
        val reasoningPatterns = listOf(
            "consider", "analyze", "propose", "optimize", "complexity",
            "algorithm", "solution", "balance", "first", "then", "finally",
        )

        codePatterns.forEach { pattern ->
            complexity += text.split(pattern).size - 1
        }

        reasoningPatterns.forEach { pattern ->
            complexity += text.split(pattern, ignoreCase = true).size - 1
        }

        // Add base complexity based on length
        complexity += (text.length / COMPLEXITY_BASE_DIVISOR)

        return complexity.coerceAtMost(MAX_COMPLEXITY)
    }

    fun detectTaskType(text: String): String {
        val lowerText = text.lowercase()

        // Code task indicators
        val codePatterns = listOf(
            "write", "implement", "function", "class", "code", "program",
            "algorithm", "debug", "fix", "refactor", "create",
        )

        // QA task indicators
        val qaPatterns = listOf(
            "what", "how", "why", "explain", "difference", "compare",
            "define", "describe", "question", "answer",
        )

        // Summarization task indicators
        val summaryPatterns = listOf(
            "summarize", "summary", "condense", "brief", "overview",
            "shorten", "abstract", "recap",
        )

        var codeScore = 0
        var qaScore = 0
        var summaryScore = 0

        codePatterns.forEach { pattern ->
            codeScore += lowerText.split(pattern).size - 1
        }

        qaPatterns.forEach { pattern ->
            qaScore += lowerText.split(pattern).size - 1
        }

        summaryPatterns.forEach { pattern ->
            summaryScore += lowerText.split(pattern).size - 1
        }

        return when {
            codeScore >= qaScore && codeScore >= summaryScore -> "code"
            qaScore >= summaryScore -> "qa"
            else -> "summarization"
        }
    }
}
