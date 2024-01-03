package com.example.backend.api.DTO;

import lombok.Builder;
@Builder
public record ImageFileDTO(Long id, String name, String type, Long productId) {}