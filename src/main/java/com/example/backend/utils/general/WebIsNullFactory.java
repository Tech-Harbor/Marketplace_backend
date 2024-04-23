package com.example.backend.utils.general;


import com.example.backend.web.Category.CategoryEntity;
import com.example.backend.web.Product.ProductDTO;
import com.example.backend.web.Product.ProductFactory;
import com.example.backend.web.User.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class WebIsNullFactory {

    private final ProductFactory productFactory;

    public List<ProductDTO> isNullProductCategory(final CategoryEntity category) {
        return Optional.ofNullable(category.getProductEntityList())
                .map(list -> list.stream()
                        .map(productFactory::makeProduct)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public List<ProductDTO> isNullProductUser(final UserEntity user) {
        return Optional.ofNullable(user.getProduct())
                .map(list -> list.stream()
                        .map(productFactory::makeProduct)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
