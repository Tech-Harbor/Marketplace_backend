package com.example.backend.web.Advertisement.store.dto;

import com.example.backend.utils.enums.Delivery;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AdvertisementUpdateDTO(String name,
                                     String descriptionAdvertisement,
                                     String characteristicAdvertisement,
                                     BigDecimal price,
                                     String location,
                                     String category,
                                     Delivery delivery,
                                     boolean auction,
                                     boolean active) { }