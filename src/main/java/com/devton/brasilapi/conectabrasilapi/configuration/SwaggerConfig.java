package com.devton.brasilapi.conectabrasilapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Feriados - BrasilAPI")
                        .version("1.0.0")
                        .description("API que consulta feriados nacionais através da BrasilAPI"));
    }
}
