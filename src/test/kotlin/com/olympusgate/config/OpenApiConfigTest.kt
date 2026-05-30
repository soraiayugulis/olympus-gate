package com.olympusgate.config

import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class OpenApiConfigTest {

    @Test
    fun `openapi configuration class should exist`() {
        val configClass = Class.forName("com.olympusgate.config.OpenApiConfig")
        assertNotNull(configClass)
    }
}
