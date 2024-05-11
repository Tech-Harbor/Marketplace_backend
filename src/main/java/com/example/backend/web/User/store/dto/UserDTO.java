package com.example.backend.web.User.store.dto;

import com.example.backend.web.Advertisement.AdvertisementDTO;
import com.example.backend.utils.enums.RegisterAuthStatus;
import com.example.backend.utils.enums.Role;
import lombok.Builder;

import java.util.List;

@Builder
public record UserDTO(Long id,
                      String image,
                      String lastname,
                      String firstname,
                      String email,
                      String phone,
                      String password,
                      List<AdvertisementDTO> advertisements,
                      RegisterAuthStatus status,
                      Role role) { }