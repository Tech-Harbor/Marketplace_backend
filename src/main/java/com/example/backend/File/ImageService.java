package com.example.backend.File;


import java.util.List;

public interface ImageService {
    ImageDTO save(ImageEntity imageEntity);
    List<ImageDTO> getAllPhoto();
}
