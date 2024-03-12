package com.example.backend.web.Category;

import com.example.backend.web.utils.WebIsNullFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFactory {

    private final WebIsNullFactory webIsNullFactory;

    public CategoryDTO makeCategory(final CategoryEntity category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .productDTOList(webIsNullFactory.isNullProductCategory(category))
                .build();
    }
}