package com.example.backend.web.User.store.dto;

import lombok.Builder;

@Builder
public record UserUpdateInfoDTO(String lastname,
                                String firstname,
                                String email,
                                String phone,
                                String password) { }