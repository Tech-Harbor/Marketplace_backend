package com.example.backend.web.Category.store.dto;


import com.example.backend.web.Advertisement.store.dto.AdvertisementDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryDTO(String name, List<AdvertisementDTO> advertisements, String image) { }