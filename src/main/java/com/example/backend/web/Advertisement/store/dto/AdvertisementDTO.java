package com.example.backend.web.Advertisement.store.dto;

import com.example.backend.web.File.store.ImageEntity;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AdvertisementDTO(Long id,
                               String name,
                               String descriptionAdvertisement,
                               String characteristicAdvertisement,
                               BigDecimal price,
                               LocalDateTime createDate,
                               List<ImageEntity> images,
                               String location,
                               String category,
                               String delivery) { }