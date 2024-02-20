package com.example.backend.security.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmailRequest(@NotNull @NotBlank @Email String email) {}