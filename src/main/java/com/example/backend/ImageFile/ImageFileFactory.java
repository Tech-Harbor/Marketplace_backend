package com.example.backend.ImageFile;

import com.example.backend.ImageFile.ImageFileDTO;
import com.example.backend.ImageFile.ImageFileEntity;
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