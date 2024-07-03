package com.example.backend.web.Advertisement.store.factory;

import com.example.backend.web.Advertisement.store.AdvertisementEntity;
import com.example.backend.web.Advertisement.store.dto.AdvertisementCreateDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AdvertisementCreateFactory implements Function<AdvertisementEntity, AdvertisementCreateDTO> {
    @Override
    public AdvertisementCreateDTO apply(final AdvertisementEntity entity) {
        return AdvertisementCreateDTO.builder()
                .descriptionAdvertisement(entity.getDescriptionAdvertisement())
                .name(entity.getName())
                .price(entity.getPrice())
                .category(entity.getCategory().getName())
                .images(entity.getImages())
                .location(entity.getLocation())
                .delivery(entity.getDelivery())
                .auction(entity.isAuction())
                .active(entity.isActive())
                .build();
    }
}