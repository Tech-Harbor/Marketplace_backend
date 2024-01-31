package com.example.backend.web.Category;


import com.example.backend.web.Product.ProductDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryDTO(Long id, @NotNull @NotBlank String category_name,
                          List<ProductDTO> productDTOList) {}