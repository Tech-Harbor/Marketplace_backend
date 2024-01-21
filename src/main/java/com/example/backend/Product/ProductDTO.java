package com.example.backend.Product;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductDTO (Long id,
                          String name,
                          String description_product,
                          String characteristic_product,
                          double price,
                          LocalDateTime createDate,
                          Long subcategoryId) {}