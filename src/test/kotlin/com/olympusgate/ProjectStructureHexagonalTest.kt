package com.olympusgate

import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.assertTrue

class ProjectStructureHexagonalTest {
    @Test
    fun `domain layer packages should exist`() {
        val domainPath = Path.of("src/main/kotlin/com/olympusgate/domain")
        assertTrue(Files.exists(domainPath), "domain package should exist")

        val modelPath = Path.of("src/main/kotlin/com/olympusgate/domain/model")
        assertTrue(Files.exists(modelPath), "domain/model package should exist")

        val portPath = Path.of("src/main/kotlin/com/olympusgate/domain/port")
        assertTrue(Files.exists(portPath), "domain/port package should exist")

        val servicePath = Path.of("src/main/kotlin/com/olympusgate/domain/service")
        assertTrue(Files.exists(servicePath), "domain/service package should exist")
    }

    @Test
    fun `application layer packages should exist`() {
        val applicationPath = Path.of("src/main/kotlin/com/olympusgate/application")
        assertTrue(Files.exists(applicationPath), "application package should exist")

        val servicePath = Path.of("src/main/kotlin/com/olympusgate/application/service")
        assertTrue(Files.exists(servicePath), "application/service package should exist")

        val dtoPath = Path.of("src/main/kotlin/com/olympusgate/application/dto")
        assertTrue(Files.exists(dtoPath), "application/dto package should exist")
    }

    @Test
    fun `infrastructure layer packages should exist`() {
        val infrastructurePath = Path.of("src/main/kotlin/com/olympusgate/infrastructure")
        assertTrue(Files.exists(infrastructurePath), "infrastructure package should exist")

        val adapterPath = Path.of("src/main/kotlin/com/olympusgate/infrastructure/adapter")
        assertTrue(Files.exists(adapterPath), "infrastructure/adapter package should exist")

        val persistencePath = Path.of("src/main/kotlin/com/olympusgate/infrastructure/persistence")
        assertTrue(Files.exists(persistencePath), "infrastructure/persistence package should exist")
    }

    @Test
    fun `api layer packages should exist`() {
        val apiPath = Path.of("src/main/kotlin/com/olympusgate/api")
        assertTrue(Files.exists(apiPath), "api package should exist")

        val controllerPath = Path.of("src/main/kotlin/com/olympusgate/api/controller")
        assertTrue(Files.exists(controllerPath), "api/controller package should exist")

        val requestPath = Path.of("src/main/kotlin/com/olympusgate/api/request")
        assertTrue(Files.exists(requestPath), "api/request package should exist")

        val responsePath = Path.of("src/main/kotlin/com/olympusgate/api/response")
        assertTrue(Files.exists(responsePath), "api/response package should exist")
    }
}
