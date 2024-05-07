package com.example.backend.utils.general;


import com.example.backend.web.Category.CategoryEntity;
import com.example.backend.web.Advertisement.AdvertisementDTO;
import com.example.backend.web.Advertisement.AdvertisementFactory;
import com.example.backend.web.User.store.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class WebIsNullFactory {

    private final AdvertisementFactory advertisementFactory;

    public List<AdvertisementDTO> isNullAdvertisementCategory(final CategoryEntity category) {
        return Optional.ofNullable(category.getAdvertisements())
                .map(list -> list.stream()
                        .map(advertisementFactory::makeAdvertisement)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public List<AdvertisementDTO> isNullAdvertisementUser(final UserEntity user) {
        return Optional.ofNullable(user.getAdvertisements())
                .map(list -> list.stream()
                        .map(advertisementFactory::makeAdvertisement)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}