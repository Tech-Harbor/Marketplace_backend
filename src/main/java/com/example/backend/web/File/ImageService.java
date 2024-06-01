package com.example.backend.web.File;


import com.example.backend.web.File.store.ImageEntity;
import com.example.backend.web.File.store.dto.ImageCreateDTO;
import com.example.backend.web.File.store.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ImageCreateDTO uploadImage(MultipartFile file);
    ImageEntity uploadImageEntity(MultipartFile file);
    List<ImageDTO> getAllPhoto();
    ImageEntity getByImage(String image);
    void imageDeleteId(String id);
}