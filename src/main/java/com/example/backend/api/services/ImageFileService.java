package com.example.backend.api.services;

import com.example.backend.api.DTO.ImageFileDTO;
import com.example.backend.store.models.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageFileService {
    ImageFileDTO uploadImage(MultipartFile image);
    void deleteIdImage(Long id);
    void setProductEntity(ProductEntity product);
    ImageFileDTO getImageFileById(Long id);
}