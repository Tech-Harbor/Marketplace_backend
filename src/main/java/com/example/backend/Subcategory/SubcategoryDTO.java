package com.example.backend.Subcategory;

import com.example.backend.Product.ProductDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record SubcategoryDTO(Long id, @NotNull @NotBlank String title, String additions, @NotNull Long categoryId, List<ProductDTO> productsList) {}