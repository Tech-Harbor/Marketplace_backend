package com.example.backend.Category;

import com.example.backend.Subcategory.SubcategoryDTO;
import com.example.backend.Subcategory.SubcategoryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.List;

@Component
@AllArgsConstructor
public class CategoryFactory {

    private final SubcategoryFactory subcategoryFactory;

    public CategoryDTO makeCategory(CategoryEntity category) {
        List<SubcategoryDTO> subcategoryDTOList = category.getSubcategoriesList() == null ? null : category.getSubcategoriesList()
                .stream()
                .map(subcategoryFactory::makeSubcategory)
                .toList();

        return CategoryDTO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .information(category.getInformation())
                .subcategoryList(subcategoryDTOList)
                .build();
    }
}