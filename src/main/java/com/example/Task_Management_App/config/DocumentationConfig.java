package com.example.Task_Management_App.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class DocumentationConfig {
    @Bean
    public OpenAPI customDocumentation() {
        return new OpenAPI().info(
                new Info().title("Mustafa Mammadli Back-end Developer")
                        .version("0.1.0")
                        .description("Bank Customer API - i")
                        .contact(
                                new Contact()
                                        .url("linkedin.com/in/mustafa-mammadli")
                                        .email("mustafamemmedli005@gmail.com")
                                        .name("Mustafa Mammadli")
                        )
        );
    }
}
