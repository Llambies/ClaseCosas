package com.owoma.frasesapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Frases Celebres")
                        .description("API REST para gestionar autores, categorias y frases celebres")
                        .version("v1")
                        .contact(new Contact().name("2 DAM Acceso a Datos")));
    }
}
