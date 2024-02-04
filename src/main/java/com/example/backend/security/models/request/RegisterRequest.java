package com.example.backend.security.models.request;

public record RegisterRequest(String lastname, String firstname, String email,
                              String password, String passwordConfirmation) {}
