package com.example.backend.File;

import lombok.Builder;

@Builder
public record ImageDTO(Long id, String name, String imageUrl, String imageId) {
}
