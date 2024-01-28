package com.example.backend.Product;

import org.springframework.stereotype.Component;

@Component
public class ProductFactory {
    public ProductDTO makeProduct(ProductEntity entity){
        return ProductDTO.builder()
                .id(entity.getId())
                .description_product(entity.getDescription_product())
                .characteristic_product(entity.getCharacteristic_product())
                .name(entity.getName())
                .price(entity.getPrice())
                .createDate(entity.getCreateDate())
                .categoryId(entity.getCategory().getId())
                .image(entity.getImage())
                .build();
    }
}