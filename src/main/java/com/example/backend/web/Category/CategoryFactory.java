package com.example.backend.web.Category;

import com.example.backend.web.Product.ProductDTO;
import com.example.backend.web.Product.ProductFactory;
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
                .category_name(category.getCategory_name())
                .productDTOList(productDTOList)
                .build();
    }
}