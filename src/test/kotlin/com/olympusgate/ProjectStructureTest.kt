package com.olympusgate

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.assertTrue

class ProjectStructureTest {
    @Test
    fun `build gradle kts should exist`() {
        val buildGradlePath = Path.of("build.gradle.kts")
        assertTrue(Files.exists(buildGradlePath), "build.gradle.kts should exist")
    }

    @Test
    fun `build gradle kts should contain spring boot plugin`() {
        val buildGradleContent = Files.readString(Path.of("build.gradle.kts"))
        assertTrue(
            "org.springframework.boot" in buildGradleContent,
            "build.gradle.kts should contain Spring Boot plugin",
        )
    }

    @Test
    fun `build gradle kts should contain kotlin plugin`() {
        val buildGradleContent = Files.readString(Path.of("build.gradle.kts"))
        assertTrue(
            "kotlin(\"jvm\")" in buildGradleContent,
            "build.gradle.kts should contain Kotlin JVM plugin",
        )
    }

    @Test
    fun `build gradle kts should contain required dependencies`() {
        val buildGradleContent = Files.readString(Path.of("build.gradle.kts"))
        assertTrue(
            "spring-boot-starter-web" in buildGradleContent,
            "build.gradle.kts should contain spring-boot-starter-web",
        )
        assertTrue(
            "spring-boot-starter-webflux" in buildGradleContent,
            "build.gradle.kts should contain spring-boot-starter-webflux",
        )
        assertTrue(
            "spring-boot-starter-data-jpa" in buildGradleContent,
            "build.gradle.kts should contain spring-boot-starter-data-jpa",
        )
        assertTrue(
            "spring-boot-starter-data-redis" in buildGradleContent,
            "build.gradle.kts should contain spring-boot-starter-data-redis",
        )
        assertTrue(
            "micrometer-registry-prometheus" in buildGradleContent,
            "build.gradle.kts should contain micrometer-registry-prometheus",
        )
    }

    @Test
    fun `build gradle kts should contain test dependencies`() {
        val buildGradleContent = Files.readString(Path.of("build.gradle.kts"))
        assertTrue(
            "spring-boot-starter-test" in buildGradleContent,
            "build.gradle.kts should contain spring-boot-starter-test",
        )
        assertTrue(
            "mockk" in buildGradleContent,
            "build.gradle.kts should contain mockk",
        )
        assertTrue(
            "testcontainers" in buildGradleContent,
            "build.gradle.kts should contain testcontainers",
        )
    }
}
