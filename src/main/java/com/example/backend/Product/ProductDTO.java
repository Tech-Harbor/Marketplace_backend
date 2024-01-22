package com.example.backend.Product;

import com.example.backend.File.ImageEntity;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ProductDTO (Long id,
                          String name,
                          String description_product,
                          String characteristic_product,
                          double price,
                          LocalDateTime createDate,
                          List<ImageEntity> image,
                          Long subcategoryId) {}