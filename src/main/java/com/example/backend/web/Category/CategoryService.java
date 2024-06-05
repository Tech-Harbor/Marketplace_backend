package com.example.backend.web.Category;

import com.example.backend.web.Category.store.CategoryEntity;
import com.example.backend.web.Category.store.dto.CategoryCreateDTO;
import com.example.backend.web.Category.store.dto.CategoryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();
    CategoryEntity getCategoryName(String name);
    CategoryDTO getCategoryDTOName(String name);
    CategoryCreateDTO create(CategoryCreateDTO categoryDTO, MultipartFile image);
    CategoryCreateDTO update(String name, CategoryCreateDTO categoryDTO, MultipartFile image);
    void deleteCategory(CategoryDTO categoryDTO);
}