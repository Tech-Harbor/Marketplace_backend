package com.example.backend.Category;

import com.example.backend.Product.ProductDTO;
import com.example.backend.Product.ProductFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CategoryFactory {

    private ProductFactory productFactory;

    public CategoryDTO makeCategory(CategoryEntity category) {
        List<ProductDTO> productDTOList =
                category.getProductEntityList() == null ? null : category.getProductEntityList()
                .stream()
                .map(productFactory::makeProduct)
                .toList();


        return CategoryDTO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .information(category.getInformation())
                .productDTOList(productDTOList)
                .build();
    }
}