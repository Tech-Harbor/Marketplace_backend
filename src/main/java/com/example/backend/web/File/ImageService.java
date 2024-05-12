package com.example.backend.web.File;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ImageDTO uploadImage(MultipartFile file);
    ImageEntity uploadImageEntity(MultipartFile file);
    List<ImageDTO> getAllPhoto();
    ImageEntity getByImage(String image);
    ImageDTO imageById(Long id);
}