package com.example.backend.web.File.store.dto;

import lombok.Builder;

@Builder
public record ImageDTO(String name, String imageUrl, String imageId) { }