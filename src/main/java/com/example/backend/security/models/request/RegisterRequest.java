package com.example.backend.security.models.request;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotNull @NotBlank @Size(min = 2, max = 20)
        @Pattern(regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$") String lastname,
        @NotNull @NotBlank @Size(min = 2, max = 20)
        @Pattern(regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$") String firstname,
        @NotNull @NotBlank @Size(max = 9) String number,
        @NotNull @NotBlank @Email @Size(min = 7, max = 35) String email,
        @NotNull @NotBlank @Size(min = 7, max = 20) @Pattern(regexp = "^(?=.*\\d)[A-Za-z\\d]+$")String password) {}