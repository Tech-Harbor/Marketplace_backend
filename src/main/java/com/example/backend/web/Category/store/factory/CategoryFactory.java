package com.example.backend.web.Category.store.factory;

import com.example.backend.utils.general.WebIsNullFactory;
import com.example.backend.web.Category.store.CategoryEntity;
import com.example.backend.web.Category.store.dto.CategoryDTO;
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
                .name(category.getName())
                .advertisements(webIsNullFactory.isNullAdvertisementCategory(category))
                .image(category.getImage().getImageUrl())
                .color(category.getColor())
                .build();
    }
}