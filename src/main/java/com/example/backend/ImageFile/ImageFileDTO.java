package com.example.backend.ImageFile;

import lombok.Builder;
@Builder
public record ImageFileDTO(Long id, String name, String type) {}