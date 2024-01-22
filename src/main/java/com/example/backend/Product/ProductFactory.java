package com.example.backend.Product;

import com.example.backend.File.ImageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductFactory {

    private final ImageFactory imageFactory;

    public ProductDTO makeProduct(ProductEntity entity){
        return ProductDTO.builder()
                .id(entity.getId())
                .description_product(entity.getDescription_product())
                .characteristic_product(entity.getCharacteristic_product())
                .name(entity.getName())
                .price(entity.getPrice())
                .createDate(entity.getCreateDate())
                .subcategoryId(entity.getSubcategory().getId())
                .image(entity.getImage())
                .build();
    }
}