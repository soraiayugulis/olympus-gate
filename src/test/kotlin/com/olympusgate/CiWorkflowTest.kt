package com.olympusgate

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.assertTrue

class CiWorkflowTest {

    @Test
    fun `github actions workflow file should exist`() {
        val workflowPath = Path.of(".github/workflows/ci.yml")
        assertTrue(Files.exists(workflowPath), ".github/workflows/ci.yml should exist")
    }

    @Test
    fun `ci workflow should contain build step`() {
        val workflowContent = Files.readString(Path.of(".github/workflows/ci.yml"))
        assertTrue(
            "gradle build" in workflowContent || "./gradlew build" in workflowContent,
            "CI workflow should contain build step"
        )
    }

    @Test
    fun `ci workflow should contain test step`() {
        val workflowContent = Files.readString(Path.of(".github/workflows/ci.yml"))
        assertTrue(
            "gradle test" in workflowContent || "./gradlew test" in workflowContent,
            "CI workflow should contain test step"
        )
    }

    @Test
    fun `ci workflow should contain linting step`() {
        val workflowContent = Files.readString(Path.of(".github/workflows/ci.yml"))
        assertTrue(
            "ktlint" in workflowContent || "detekt" in workflowContent,
            "CI workflow should contain linting step"
        )
    }
}
