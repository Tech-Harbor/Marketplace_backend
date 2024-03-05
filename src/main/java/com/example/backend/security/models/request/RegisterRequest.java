package com.example.backend.security.models.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @NotNull @NotBlank(message = "Поле не повинно бути порожнім")
        @Size(min = 2, max = 20, message = "Довжина firstname має бути між 2 та 20 символами")
        @Pattern(regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$",
                message = "lastname має відповідати вказаному формату") String lastname,
        @NotNull @NotBlank(message = "Поле не повинно бути порожнім")
        @Size(min = 2, max = 20, message = "Довжина firstname має бути між 2 та 20 символами")
        @Pattern(regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$",
                message = "firstname має відповідати вказаному формату") String firstname,
        @NotNull @NotBlank(message = "Поле не повинно бути порожнім")
        @Size(max = 9, message = "Номер не повинен перевищувати 9 символів") String phone,
        @NotNull @NotBlank(message = "Поле не повинно бути порожнім")
        @Email(message = "Введіть коректну адресу електронної пошти з @")
        @Size(min = 7, max = 35, message = "Довжина email має бути між 7 та 35 символами") String email,
        @NotNull @NotBlank(message = "Поле не повинно бути порожнім")
        @Size(min = 7, max = 20, message = "Довжина password має бути між 7 та 20 символами")
        @Pattern(regexp = "^(?=.*\\d)[A-Za-z\\d]+$",
                message = "password має відповідати вказаному формату") String password) {}