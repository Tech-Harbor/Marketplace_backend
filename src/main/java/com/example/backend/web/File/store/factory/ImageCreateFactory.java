package com.example.backend.web.File.store.factory;


import com.example.backend.web.File.store.ImageEntity;
import com.example.backend.web.File.store.dto.ImageCreateDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ImageCreateFactory implements Function<ImageEntity, ImageCreateDTO> {
    @Override
    public ImageCreateDTO apply(final ImageEntity imageEntity) {
        return ImageCreateDTO.builder()
                .imageUrl(imageEntity.getImageUrl())
                .build();
    }
}