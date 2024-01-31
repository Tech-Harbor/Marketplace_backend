package com.example.backend.Config.Swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact =  @Contact(),
                description = "Marketplace_backend",
                title = "OpenApiMerketplace",
                version = "1.0.0"
        )
)
public class OpenApiSwaggerConfig {}