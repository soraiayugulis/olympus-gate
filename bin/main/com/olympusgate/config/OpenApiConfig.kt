package com.olympusgate.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun olympusGateOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Olympus Gate API")
                    .description("AI Model Gateway - Intelligent middleware for routing AI model requests")
                    .version("1.0.0")
                    .license(
                        License()
                            .name("MIT")
                            .url("https://opensource.org/licenses/MIT"),
                    ),
            )
    }
}
