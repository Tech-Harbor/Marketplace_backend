package com.example.backend.web.Category;


import com.example.backend.web.Product.ProductDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryDTO(Long id,
                          @NotNull @NotBlank @Size(min = 5, max = 20) String category_name,
                          List<ProductDTO> productDTOList) {}