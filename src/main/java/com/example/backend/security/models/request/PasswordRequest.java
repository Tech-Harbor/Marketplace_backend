package com.example.backend.security.models.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record PasswordRequest(
        @NotNull @NotBlank @Email String email,
        @NotNull @NotBlank @Size(min = 7, max = 20) @Pattern(regexp = "^(?=.*\\d)[A-Za-z\\d]+$") String password) { }