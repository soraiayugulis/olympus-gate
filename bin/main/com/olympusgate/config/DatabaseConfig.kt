package com.olympusgate.config

import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfig {
    // Custom Flyway migration strategy can be added here if needed
    // For now, we rely on Spring Boot's default Flyway auto-configuration
}
