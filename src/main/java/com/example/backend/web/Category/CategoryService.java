package com.example.backend.web.Category;

import java.util.List;
public interface CategoryService {
    List<CategoryDTO> getAll();
    CategoryEntity getCategoryName(String categoryName);
    CategoryDTO getOneById(Long id);
    CategoryDTO create(CategoryDTO categoryDTO);
    CategoryDTO update(Long categoryId, CategoryDTO categoryDTO);
    void deleteId(Long id);
    List<CategoryEntity> getFilterCategory(String categoryName);
}