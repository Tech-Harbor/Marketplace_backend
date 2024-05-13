package com.example.backend.web.Category.store.factory;

import com.example.backend.web.Category.store.CategoryEntity;
import com.example.backend.web.Category.store.dto.CategoryCreateDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryCreateFactory implements Function<CategoryEntity, CategoryCreateDTO> {
    @Override
    public CategoryCreateDTO apply(final CategoryEntity category) {
        return CategoryCreateDTO.builder()
                .categoryName(category.getCategoryName())
                .image(category.getImage().getImageUrl())
                .build();
    }
}