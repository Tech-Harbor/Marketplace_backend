package com.example.backend.Subcategory;

import com.example.backend.Product.ProductDTO;
import com.example.backend.Product.ProductFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class SubcategoryFactory {

    private final ProductFactory productFactory;

    public SubcategoryDTO makeSubcategory(SubcategoryEntity subcategory) {
        List<ProductDTO> productDTOList = subcategory.getProductsList() == null ? null : subcategory.getProductsList()
                .stream()
                .map(productFactory::makeProduct)
                .toList();

        return SubcategoryDTO.builder()
                .id(subcategory.getId())
                .title(subcategory.getTitle())
                .additions(subcategory.getAdditions())
                .categoryId(subcategory.getCategory().getId())
                .productsList(productDTOList)
                .build();
    }
}