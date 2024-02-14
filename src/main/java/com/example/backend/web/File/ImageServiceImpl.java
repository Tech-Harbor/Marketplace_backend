package com.example.backend.web.File;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;
    private final ImageFactory imageFactory;

    @Override
    public ImageDTO save(ImageEntity imageEntity) {
        return imageFactory.makeImageFactory(imageRepository.save(imageEntity));
    }

    @Override
    public List<ImageDTO> getAllPhoto() {
        return imageRepository.findAll().stream()
                .map(imageFactory::makeImageFactory)
                .collect(Collectors.toList());
    }

    @Override
    public ImageDTO imageById(Long id) {
        return imageRepository.getReferenceById(id);
    }
}