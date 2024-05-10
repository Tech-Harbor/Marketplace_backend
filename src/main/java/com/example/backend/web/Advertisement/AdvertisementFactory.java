package com.example.backend.web.Advertisement;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AdvertisementFactory implements Function<AdvertisementEntity, AdvertisementDTO> {
    @Override
    public AdvertisementDTO apply(final AdvertisementEntity entity) {
        return AdvertisementDTO.builder()
                .id(entity.getId())
                .descriptionAdvertisement(entity.getDescriptionAdvertisement())
                .characteristicAdvertisement(entity.getCharacteristicAdvertisement())
                .name(entity.getName())
                .price(entity.getPrice())
                .createDate(entity.getCreateDate())
                .category(entity.getCategory().getCategoryName())
                .images(entity.getImages())
                .location(entity.getLocation())
                .build();
    }
}