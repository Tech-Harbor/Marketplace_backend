package com.example.backend.security.models.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import static com.example.backend.utils.general.Constants.EMPTY_FIELD;

@Builder
public record PasswordRequest(
        @NotNull @NotBlank(message = EMPTY_FIELD)
        @Size(min = 7, max = 20)
        @Pattern(regexp = "^(?=.*\\d)[A-Za-z\\d]+$",
                message = "password має відповідати вказаному формату") String password) { }