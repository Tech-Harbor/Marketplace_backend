package com.example.backend.ImageFile;

import com.example.backend.ImageFile.ImageFileDTO;
import com.example.backend.Product.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageFileService {
    ImageFileDTO uploadImage(MultipartFile image);
    void deleteIdImage(Long id);
    void setProductEntity(ProductEntity product);
    ImageFileDTO getImageFileById(Long id);
    ImageFileEntity getImageById(Long id);
}