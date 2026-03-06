package com.kabq.arquitectura_hexagonal.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Arquitectura Hexagonal")
                        .version("1.0.0")
                        .description("Documentación de los endpoints REST de la aplicación"));
    }
}
