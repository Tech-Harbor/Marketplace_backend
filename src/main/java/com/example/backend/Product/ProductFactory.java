package com.example.backend.Product;

import com.example.backend.ImageFile.ImageFileFactory;
import com.example.backend.Product.ProductDTO;
import com.example.backend.Product.ProductEntity;
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
                .name(entity.getName())
                .price(entity.getPrice())
                .createDate(entity.getCreateDate())
                .image(entity.getImage().stream()
                        .map(imageFileFactory::makeImageFile)
                        .collect(Collectors.toList()))
                .build();
    }
}