package com.olympusgate

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.assertTrue

class DockerComposeTest {
    @Test
    fun `docker-compose file should exist`() {
        val dockerComposePath = Path.of("docker-compose.yml")
        assertTrue(Files.exists(dockerComposePath), "docker-compose.yml should exist")
    }

    @Test
    fun `docker-compose should contain postgresql service`() {
        val dockerComposeContent = Files.readString(Path.of("docker-compose.yml"))
        assertTrue(
            "postgres" in dockerComposeContent || "postgresql" in dockerComposeContent,
            "docker-compose should contain PostgreSQL service",
        )
    }

    @Test
    fun `docker-compose should contain redis service`() {
        val dockerComposeContent = Files.readString(Path.of("docker-compose.yml"))
        assertTrue(
            "redis" in dockerComposeContent,
            "docker-compose should contain Redis service",
        )
    }

    @Test
    fun `env example file should exist`() {
        val envExamplePath = Path.of(".env.example")
        assertTrue(Files.exists(envExamplePath), ".env.example should exist")
    }
}
