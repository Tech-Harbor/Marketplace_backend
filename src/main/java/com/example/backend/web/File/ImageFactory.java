package com.example.backend.web.File;


import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ImageFactory implements Function<ImageEntity, ImageDTO> {
    @Override
    public ImageDTO apply(final ImageEntity imageEntity) {
        return ImageDTO.builder()
                .imageUrl(imageEntity.getImageUrl())
                .build();
    }
}