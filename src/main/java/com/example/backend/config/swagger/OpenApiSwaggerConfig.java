package com.example.backend.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import static com.example.backend.utils.general.Constants.*;

@OpenAPIDefinition(
        info = @Info(
                contact =  @Contact(),
                title = "Oranger API ",
                description = "Documentation for access to the Oranger resources via REST API.",
                version = "1.2.1"
        )
)
@SecurityScheme(
        name = BEARER_AUTHENTICATION,
        description = "JWT auth description",
        scheme = BEARER,
        type = SecuritySchemeType.HTTP,
        bearerFormat = JWT,
        in = SecuritySchemeIn.HEADER
)
public class OpenApiSwaggerConfig { }