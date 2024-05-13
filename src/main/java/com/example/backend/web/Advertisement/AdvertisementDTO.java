package com.example.backend.web.Advertisement;

import com.example.backend.web.File.store.ImageEntity;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AdvertisementDTO(Long id,
                               String name,
                               String descriptionAdvertisement,
                               String characteristicAdvertisement,
                               double price,
                               LocalDateTime createDate,
                               List<ImageEntity> images,
                               String location,
                               String category) { }