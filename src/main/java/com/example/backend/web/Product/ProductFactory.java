package com.example.backend.web.Product;

import org.springframework.stereotype.Component;

@Component
public class ProductFactory {
    public ProductDTO makeProduct(final ProductEntity entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .descriptionProduct(entity.getDescriptionProduct())
                .characteristicProduct(entity.getCharacteristicProduct())
                .name(entity.getName())
                .price(entity.getPrice())
                .createDate(entity.getCreateDate())
                .categoryId(entity.getCategory().getId())
                .image(entity.getImage())
                .build();
    }
}