package com.example.backend.Category;

import com.example.backend.Subcategory.SubcategoryDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryDTO(Long id, @NotNull @NotBlank String title, @NotNull @NotBlank String information, List<SubcategoryDTO> subcategoryList) {}