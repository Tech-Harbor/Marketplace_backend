package com.example.backend.Category;

import java.util.List;
public interface CategoryService {

    List<CategoryDTO> getAll();

    CategoryEntity getById(Long id);

    CategoryDTO getOneById(Long id);

    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(Long categoryId, CategoryDTO categoryDTO);

    void delete(Long id);
}