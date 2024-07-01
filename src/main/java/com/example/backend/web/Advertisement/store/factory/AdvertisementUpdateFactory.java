package com.example.backend.web.Advertisement.store.factory;

import com.example.backend.web.Advertisement.store.AdvertisementEntity;
import com.example.backend.web.Advertisement.store.dto.AdvertisementUpdateDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AdvertisementUpdateFactory implements Function<AdvertisementEntity, AdvertisementUpdateDTO> {
    @Override
    public AdvertisementUpdateDTO apply(final AdvertisementEntity entity) {
        return AdvertisementUpdateDTO.builder()
                .descriptionAdvertisement(entity.getDescriptionAdvertisement())
                .characteristicAdvertisement(entity.getCharacteristicAdvertisement())
                .name(entity.getName())
                .price(entity.getPrice())
                .category(entity.getCategory().getName())
                .location(entity.getLocation())
                .delivery(entity.getDelivery())
                .auction(entity.isAuction())
                .build();
    }
}