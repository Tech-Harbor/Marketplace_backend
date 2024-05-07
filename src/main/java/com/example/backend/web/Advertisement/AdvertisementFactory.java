package com.example.backend.web.Advertisement;

import org.springframework.stereotype.Component;

@Component
public class AdvertisementFactory {
    public AdvertisementDTO makeAdvertisement(final AdvertisementEntity entity) {
        return AdvertisementDTO.builder()
                .id(entity.getId())
                .descriptionAdvertisement(entity.getDescriptionAdvertisement())
                .characteristicAdvertisement(entity.getCharacteristicAdvertisement())
                .name(entity.getName())
                .price(entity.getPrice())
                .createDate(entity.getCreateDate())
                .categoryId(entity.getCategory().getId())
                .images(entity.getImages())
                .location(entity.getLocation())
                .build();
    }
}