package com.example.backend.web.User.store.dto;

import com.example.backend.web.File.ImageEntity;
import lombok.Builder;

@Builder
public record UserInfoDTO(String lastname,
                          ImageEntity image,
                          String firstname,
                          String email,
                          String phone) { }