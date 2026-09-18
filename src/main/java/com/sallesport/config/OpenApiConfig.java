package com.sallesport.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI salleDeSportOpenApi() {
        return new OpenAPI().info(
                new Info()
                        .title("API Salle de Sport")
                        .description("Exo 5 - Gestion des adhérents, coachs, cours collectifs et inscriptions")
                        .version("1.0")
        );
    }
}
