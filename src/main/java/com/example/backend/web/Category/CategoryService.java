package com.example.backend.web.Category;

import java.util.List;
public interface CategoryService {
    List<CategoryDTO> getAll();
    CategoryEntity getCategoryName(String categoryName);
    CategoryDTO getCategoryDTOName(String categoryName);
    CategoryDTO create(CategoryDTO categoryDTO);
    CategoryDTO update(Long categoryId, CategoryDTO categoryDTO);
    void deleteId(Long id);
}