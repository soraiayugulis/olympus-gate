package com.olympusgate.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = ["spring.redis.host"], matchIfMissing = false)
class RedisConfig {
    // Redis connection is auto-configured by Spring Boot
    // Custom RedisTemplate configuration can be added here if needed
}
