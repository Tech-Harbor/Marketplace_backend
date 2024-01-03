package com.example.backend.api.factory;

import com.example.backend.api.DTO.ImageFileDTO;
import com.example.backend.store.models.ImageFileEntity;
import org.springframework.stereotype.Component;

@Component
public class ImageFileFactory {
    public ImageFileDTO makeImageFile(ImageFileEntity file){
        return ImageFileDTO.builder()
                .id(file.getId())
                .type(file.getType())
                .name(file.getName())
//                .productId(file.getProduct().getId())
                .build();
    }
}