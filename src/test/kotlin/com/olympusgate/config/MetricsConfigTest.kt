package com.olympusgate.config

import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class MetricsConfigTest {

    @Test
    fun `metrics configuration class should exist`() {
        val configClass = Class.forName("com.olympusgate.config.MetricsConfig")
        assertNotNull(configClass)
    }
}
