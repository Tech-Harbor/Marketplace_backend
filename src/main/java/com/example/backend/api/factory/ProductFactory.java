package com.example.backend.api.factory;

import com.example.backend.api.DTO.ProductDTO;
import com.example.backend.store.models.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductFactory {

    private final ImageFileFactory imageFileFactory;

    public ProductDTO makeProduct(ProductEntity entity){
        return ProductDTO.builder()
                .id(entity.getId())
                .description_product(entity.getDescription_product())
                .characteristic_product(entity.getCharacteristic_product())
                .number(entity.getNumber())
                .category(entity.getCategory())
                .seller(entity.getSeller())
                .name(entity.getName())
                .price(entity.getPrice())
                .createDate(entity.getCreateDate())
                .image(entity.getImage().stream()
                        .map(imageFileFactory::makeImageFile)
                        .collect(Collectors.toList()))
                .build();
    }
}