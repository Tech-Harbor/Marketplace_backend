package com.example.backend.Product;

import com.example.backend.ImageFile.ImageFileDTO;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ProductDTO (Long id,
                          String name,
                          String description_product,
                          String characteristic_product,
                          double price,
                          LocalDateTime createDate,
                          List<ImageFileDTO> image){}