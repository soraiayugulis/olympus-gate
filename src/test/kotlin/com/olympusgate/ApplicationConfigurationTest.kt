package com.olympusgate

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(
    properties = [
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
            "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration," +
            "org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
    ]
)
@AutoConfigureWebMvc
class ApplicationConfigurationTest {

    @Autowired
    private lateinit var environment: Environment

    @Test
    fun `application context should load`() {
        assertNotNull(environment)
    }

    @Test
    fun `server port should be configured`() {
        val port = environment.getProperty("server.port")
        assertNotNull(port)
        assertEquals("8080", port)
    }

    @Test
    fun `application name should be configured`() {
        val appName = environment.getProperty("spring.application.name")
        assertNotNull(appName)
        assertEquals("olympus-gate", appName)
    }

    @Test
    fun `logging configuration should be present`() {
        val loggingLevel = environment.getProperty("logging.level.root")
        assertNotNull(loggingLevel)
    }
}
