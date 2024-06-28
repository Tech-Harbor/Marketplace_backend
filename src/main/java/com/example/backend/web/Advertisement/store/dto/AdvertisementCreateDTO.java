package com.example.backend.web.Advertisement.store.dto;

import com.example.backend.utils.enums.Delivery;
import com.example.backend.web.File.store.ImageEntity;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
public record AdvertisementCreateDTO(String name,
                                     String descriptionAdvertisement,
                                     BigDecimal price,
                                     List<ImageEntity> images,
                                     String location,
                                     String category,
                                     Set<Delivery> delivery) { }