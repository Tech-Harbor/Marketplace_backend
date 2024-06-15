package com.example.backend.security.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static com.example.backend.utils.general.Constants.EMPTY_FIELD;

@Builder
public record EmailRequest(@NotNull @NotBlank(message = EMPTY_FIELD) @Email String email) { }