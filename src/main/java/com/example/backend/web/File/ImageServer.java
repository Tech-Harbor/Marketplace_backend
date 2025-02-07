package com.example.backend.web.File;


import com.example.backend.web.File.store.ImageEntity;
import com.example.backend.web.File.store.dto.ImageCreateDTO;
import com.example.backend.web.File.store.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageServer {
    ImageCreateDTO uploadImage(MultipartFile file);
    ImageEntity uploadImageEntity(MultipartFile file);
    List<ImageDTO> getAllPhoto();
    void imageDeleteId(String id);
}