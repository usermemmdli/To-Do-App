package com.example.Task_Management_App.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
        security = @SecurityRequirement(name = "BearerAuth")
)
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class DocumentationConfig {
    @Bean
    public OpenAPI customDocumentation() {
        return new OpenAPI().info(
                new io.swagger.v3.oas.models.info.Info().title("Mustafa Mammadli")
                        .version("0.1.0")
                        .description("To-Do API")
                        .contact(
                                new Contact()
                                        .url("linkedin.com/in/mustafa-mammadli")
                                        .email("mustafamemmedli005@gmail.com")
                                        .name("Mustafa Mammadli")
                        )
        );
    }
}
