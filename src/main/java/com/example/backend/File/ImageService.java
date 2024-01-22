package com.example.backend.File;


import java.util.List;

public interface ImageService {
    void save(ImageEntity imageEntity);
    List<ImageEntity> list();
}
