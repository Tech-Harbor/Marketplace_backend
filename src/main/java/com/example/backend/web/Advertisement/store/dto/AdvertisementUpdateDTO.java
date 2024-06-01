package com.example.backend.web.Advertisement.store.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AdvertisementUpdateDTO(String name,
                                     String descriptionAdvertisement,
                                     String characteristicAdvertisement,
                                     BigDecimal price,
                                     String location,
                                     String category,
                                     String delivery) { }