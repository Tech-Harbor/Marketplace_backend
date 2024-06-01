package com.example.backend.web.File.store.factory;


import com.example.backend.web.File.store.ImageEntity;
import com.example.backend.web.File.store.dto.ImageDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ImageFactory implements Function<ImageEntity, ImageDTO> {
    @Override
    public ImageDTO apply(final ImageEntity imageEntity) {
        return ImageDTO.builder()
                .name(imageEntity.getName())
                .imageUrl(imageEntity.getImageUrl())
                .imageId(imageEntity.getImageId())
                .build();
    }
}