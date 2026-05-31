package com.olympusgate.config

import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class RedisConfigTest {
    @Test
    fun `redis configuration class should exist`() {
        val configClass = Class.forName("com.olympusgate.config.RedisConfig")
        assertNotNull(configClass)
    }
}
