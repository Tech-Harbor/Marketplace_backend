package com.example.backend.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact =  @Contact(),
                description = "Marketplace_backend",
                title = "OpenApiMarketplace",
                version = "1.1.0"
        )
)
public class OpenApiSwaggerConfig {}