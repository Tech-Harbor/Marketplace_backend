package com.example.backend.security.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import static com.example.backend.utils.general.Constants.*;

@Builder
public record RegisterRequest(
        @NotNull @NotBlank(message = EMPTY_FIELD)
        @Size(min = 2, max = 20, message = "Довжина lastname має бути між 2 та 20 символами")
        @Pattern(regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$",
                message = "lastname " + FORMAT_MESSAGE) String lastname,
        @NotNull @NotBlank(message = EMPTY_FIELD)
        @Size(min = 2, max = 20, message = "Довжина firstname має бути між 2 та 20 символами")
        @Pattern(regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$",
                message = "firstname " + FORMAT_MESSAGE) String firstname,
        @NotNull @NotBlank(message = EMPTY_FIELD)
        @Size(min = 12, max = 12, message = "Номер не валідний, повинен містить 12 символів") String phone,
        @NotNull @NotBlank(message = EMPTY_FIELD)
        @Email(message = "Введіть коректну адресу електронної пошти з @")
        @Size(min = 7, max = 35, message = "Довжина email має бути між 7 та 35 символами") String email,
        @NotNull @NotBlank(message = EMPTY_FIELD)
        @Size(min = 7, max = 20, message = "Довжина password має бути між 7 та 20 символами")
        @Pattern(regexp = "^(?=.*\\d)[A-Za-z\\d]+$", message = "password " + FORMAT_MESSAGE) String password) { }