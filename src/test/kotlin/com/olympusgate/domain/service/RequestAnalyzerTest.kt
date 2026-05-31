package com.olympusgate.domain.service

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RequestAnalyzerTest {
    @Test
    fun `should estimate token count accurately for simple text`() {
        val analyzer = RequestAnalyzer()
        val prompt = "Hello, how are you?"
        val tokenCount = analyzer.estimateTokenCount(prompt)
        assertTrue(tokenCount > 0)
    }

    @Test
    fun `should estimate token count for code snippet`() {
        val analyzer = RequestAnalyzer()
        val code = """
            fun main() {
                println("Hello World")
            }
        """.trimIndent()
        val tokenCount = analyzer.estimateTokenCount(code)
        assertTrue(tokenCount > 0)
    }

    @Test
    fun `should handle empty prompts`() {
        val analyzer = RequestAnalyzer()
        val tokenCount = analyzer.estimateTokenCount("")
        assertEquals(0, tokenCount)
    }

    @Test
    fun `should handle very long prompts`() {
        val analyzer = RequestAnalyzer()
        val longPrompt = "a".repeat(10000)
        val tokenCount = analyzer.estimateTokenCount(longPrompt)
        assertTrue(tokenCount > 0)
    }

    @Test
    fun `should detect code complexity`() {
        val analyzer = RequestAnalyzer()
        val complexCode = """
            fun complexFunction(data: List<String>): String {
                return data.map { it.uppercase() }.joinToString(",")
            }
        """.trimIndent()
        val complexity = analyzer.analyzeComplexity(complexCode)
        assertTrue(complexity > 0)
    }

    @Test
    fun `should detect reasoning complexity`() {
        val analyzer = RequestAnalyzer()
        val reasoningText = """
            Consider the following problem: We need to optimize the algorithm for large datasets.
            First, analyze the time complexity. Then, consider space complexity.
            Finally, propose a solution that balances both.
        """.trimIndent()
        val complexity = analyzer.analyzeComplexity(reasoningText)
        assertTrue(complexity > 0)
    }

    @Test
    fun `should classify code task type`() {
        val analyzer = RequestAnalyzer()
        val codePrompt = "Write a function to sort an array in Kotlin"
        val taskType = analyzer.detectTaskType(codePrompt)
        assertEquals("code", taskType)
    }

    @Test
    fun `should classify qa task type`() {
        val analyzer = RequestAnalyzer()
        val qaPrompt = "What is the difference between HashMap and TreeMap?"
        val taskType = analyzer.detectTaskType(qaPrompt)
        assertEquals("qa", taskType)
    }

    @Test
    fun `should classify summarization task type`() {
        val analyzer = RequestAnalyzer()
        val summaryPrompt = "Summarize the following text about machine learning"
        val taskType = analyzer.detectTaskType(summaryPrompt)
        assertEquals("summarization", taskType)
    }
}
