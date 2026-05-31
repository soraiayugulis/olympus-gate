package com.olympusgate

import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class ApplicationConfigurationTest {
    @Test
    fun `application configuration class should exist`() {
        val appClass = Class.forName("com.olympusgate.OlympusGateApplication")
        assertNotNull(appClass)
    }
}
