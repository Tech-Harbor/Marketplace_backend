package com.example.backend.security.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthRequest(
        @NotNull @NotBlank(message = "Поле не повинно бути порожнім") @Email String email,
        @NotNull @NotBlank(message = "Поле не повинно бути порожнім") String password) {}