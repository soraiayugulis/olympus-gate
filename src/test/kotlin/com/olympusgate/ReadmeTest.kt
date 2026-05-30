package com.olympusgate

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.assertTrue

class ReadmeTest {

    @Test
    fun `readme should exist`() {
        val readmePath = Path.of("README.md")
        assertTrue(Files.exists(readmePath), "README.md should exist")
    }

    @Test
    fun `readme should contain project name`() {
        val readmeContent = Files.readString(Path.of("README.md"))
        assertTrue(
            "Olympus Gate" in readmeContent,
            "README should contain project name"
        )
    }

    @Test
    fun `readme should contain setup instructions`() {
        val readmeContent = Files.readString(Path.of("README.md"))
        assertTrue(
            "Setup" in readmeContent || "Getting Started" in readmeContent,
            "README should contain setup instructions"
        )
    }

    @Test
    fun `readme should contain stack information`() {
        val readmeContent = Files.readString(Path.of("README.md"))
        assertTrue(
            "Stack" in readmeContent || "Technology" in readmeContent,
            "README should contain stack information"
        )
    }
}
