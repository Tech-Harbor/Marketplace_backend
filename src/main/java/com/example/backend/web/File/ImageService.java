package com.example.backend.web.File;


import java.util.List;

public interface ImageService {
    ImageDTO save(ImageEntity imageEntity);
    List<ImageDTO> getAllPhoto();

    ImageDTO imageById(Long id);
}
