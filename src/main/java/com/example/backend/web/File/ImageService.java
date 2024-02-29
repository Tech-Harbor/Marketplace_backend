package com.example.backend.web.File;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ImageDTO uploadImage(MultipartFile file);
    List<ImageDTO> getAllPhoto();
    ImageDTO imageById(Long id);
}