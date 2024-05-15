package com.example.backend.security.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        @Schema(example = "404", description = "Статус помилки")
        int status,
        @Schema(example = "Error message", description = "Опис помилки")
        String message) { }