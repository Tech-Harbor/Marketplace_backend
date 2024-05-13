package com.example.backend.web.Category.store.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryCreateDTO(@NotNull @NotBlank @Size(min = 5, max = 20) String categoryName, String image) { }