package com.example.backend.File;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;
    private final ImageFactory imageFactory;

    @Override
    public void save(ImageEntity imageEntity) {
        imageRepository.save(imageEntity);
    }

    @Override
    public List<ImageEntity> list() {
        return imageRepository.findAll();
    }
}
