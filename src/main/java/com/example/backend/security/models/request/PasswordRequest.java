package com.example.backend.security.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PasswordRequest(@NotNull @NotBlank @Size(min = 7, max = 20) @Pattern(regexp = "^(?=.*\\d)[A-Za-z\\d]+$")
                              String password) {}