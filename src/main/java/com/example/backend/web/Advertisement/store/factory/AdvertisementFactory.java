package com.example.backend.web.Advertisement.store.factory;

import com.example.backend.web.Advertisement.store.AdvertisementEntity;
import com.example.backend.web.Advertisement.store.dto.AdvertisementDTO;
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
                .category(entity.getCategory().getName())
                .images(entity.getImages())
                .location(entity.getLocation())
                .delivery(entity.getDelivery())
                .build();
    }
}