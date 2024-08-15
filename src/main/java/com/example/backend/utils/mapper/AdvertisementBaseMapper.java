package com.example.backend.utils.mapper;

import com.example.backend.web.Advertisement.store.AdvertisementEntity;
import com.example.backend.web.Advertisement.store.dto.AdvertisementDTO;
import com.example.backend.web.Advertisement.store.mapper.AdvertisementMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Named("AdvertisementBaseMapper")
public class AdvertisementBaseMapper {

    private final AdvertisementMapper advertisementMapper;

    @Named("getAllAdvertisements")
    public List<AdvertisementDTO> getAllAdvertisements(final List<AdvertisementEntity> advertisement) {
        return Collections.singletonList(advertisementMapper
                .advertisementMapperDTO((AdvertisementEntity) advertisement)
        );
    }
}