package com.example.backend.web.Category;

import com.example.backend.utils.general.WebIsNullFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CategoryFactory implements Function<CategoryEntity, CategoryDTO> {

    private final WebIsNullFactory webIsNullFactory;

    @Override
    public CategoryDTO apply(final CategoryEntity category) {
        return CategoryDTO.builder()
                .categoryName(category.getCategoryName())
                .advertisements(webIsNullFactory.isNullAdvertisementCategory(category))
                .image(category.getImage().getImageUrl())
                .build();
    }
}