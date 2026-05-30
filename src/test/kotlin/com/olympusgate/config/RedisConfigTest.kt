package com.olympusgate.config

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

@SpringBootTest(
    properties = [
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
            "org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration," +
            "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration",
    ],
)
class RedisConfigTest {
    @Test
    fun `redis configuration class should exist`() {
        val configClass = Class.forName("com.olympusgate.config.RedisConfig")
        assertNotNull(configClass)
    }
}
