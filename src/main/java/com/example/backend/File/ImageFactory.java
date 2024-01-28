package com.example.backend.File;


import org.springframework.stereotype.Component;

@Component
public class ImageFactory {
    public ImageDTO makeImageFactory(ImageEntity imageEntity){
        return ImageDTO.builder()
                .id(imageEntity.getId())
                .imageUrl(imageEntity.getImageUrl())
                .imageId(imageEntity.getImageId())
                .name(imageEntity.getName())
                .build();
    }
}
