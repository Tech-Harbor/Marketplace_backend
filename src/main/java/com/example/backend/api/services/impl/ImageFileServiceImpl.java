package com.example.backend.api.services.impl;

import com.example.backend.api.DTO.ImageFileDTO;
import com.example.backend.api.factory.ImageFileFactory;
import com.example.backend.api.services.ImageFileService;
import com.example.backend.store.models.ImageFileEntity;
import com.example.backend.store.models.ProductEntity;
import com.example.backend.store.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageFileServiceImpl implements ImageFileService {

    private final ImageFileRepository imageFileRepository;
    private final ImageFileFactory imageFileFactory;
    private final ImageUploadServiceImpl imageUploadService;

    @Override
    public ImageFileDTO uploadImage(MultipartFile image) {
        String filename = imageUploadService.getUniqueFilename(image.getOriginalFilename() + " ");
        imageUploadService.saveImage(image, filename);

        ImageFileEntity file = ImageFileEntity.builder()
                .name(filename)
                .type(image.getContentType())
                .build();

        return imageFileFactory.makeImageFile(imageFileRepository.save(file));
    }
    @Override
    public void setProductEntity(ProductEntity product) {
        List<ImageFileEntity> images = product.getImage();
        for (ImageFileEntity image : images) {
            image.setProduct(product);
            imageFileRepository.save(image);
        }
    }

    @Override
    public ImageFileDTO getImageFileById(Long id) {
        return imageFileFactory.makeImageFile(imageFileRepository.getReferenceById(id));
    }

    @Override
    public void deleteIdImage(Long id) {
        imageFileRepository.deleteById(id);
    }
}