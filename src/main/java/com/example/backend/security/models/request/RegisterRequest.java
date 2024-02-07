package com.example.backend.security.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotNull @NotBlank @Size(min = 3, max = 20) String lastname,
                              @NotNull @NotBlank @Size(min = 3, max = 20) String firstname,
                              @NotNull @NotBlank @Size(max = 9) String number, @Email String email,
                              @NotNull @NotBlank @Size(min = 3, max = 20) String password) {}