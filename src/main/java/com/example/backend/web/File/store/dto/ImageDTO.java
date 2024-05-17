package com.example.backend.web.File.store.dto;

import lombok.Builder;

@Builder
public record ImageDTO(Long id, String name, String imageUrl, String imageId) { }