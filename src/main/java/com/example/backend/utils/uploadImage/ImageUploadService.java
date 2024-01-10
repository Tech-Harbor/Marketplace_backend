package com.example.backend.utils.uploadImage;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    
    boolean saveImage(MultipartFile file, String filename);

    String getUniqueFilename(String originalFilename);
}