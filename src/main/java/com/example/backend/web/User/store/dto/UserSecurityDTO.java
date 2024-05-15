package com.example.backend.web.User.store.dto;

import com.example.backend.utils.enums.RegisterAuthStatus;
import com.example.backend.utils.enums.Role;
import lombok.Builder;

@Builder
public record UserSecurityDTO(String lastname,
                              String firstname,
                              String email,
                              String phone,
                              String password,
                              RegisterAuthStatus status,
                              Role role,
                              Boolean enabled) { }