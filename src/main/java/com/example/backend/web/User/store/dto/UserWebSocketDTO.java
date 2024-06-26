package com.example.backend.web.User.store.dto;

import com.example.backend.utils.enums.Status;
import com.example.backend.web.Advertisement.store.dto.AdvertisementDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record UserWebSocketDTO(String image,
                               String lastname,
                               String firstname,
                               Status status,
                               List<AdvertisementDTO> advertisements) { }