package com.example.backend.web.User.store.dto;

import lombok.Builder;

@Builder
public record UserInfoDTO(String lastname,
                          String image,
                          String firstname,
                          String email,
                          String phone) { }