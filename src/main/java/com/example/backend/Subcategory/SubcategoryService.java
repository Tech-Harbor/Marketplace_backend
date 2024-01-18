package com.example.backend.Subcategory;

import java.util.List;

public interface SubcategoryService {
    List<SubcategoryDTO> getAll();

    SubcategoryEntity getById(Long id);

    SubcategoryDTO getOneById(Long id);

    SubcategoryDTO create(SubcategoryDTO subcategoryDTO);

    SubcategoryDTO update(Long subcategoryId, SubcategoryDTO subcategoryDTO);

    void delete(Long id);
}