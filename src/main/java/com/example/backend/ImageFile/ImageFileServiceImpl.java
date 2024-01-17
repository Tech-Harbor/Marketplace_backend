package com.example.backend.ImageFile;

import com.example.backend.Product.ProductEntity;
import com.example.backend.utils.uploadImage.ImageUploadServiceImpl;
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
        imageFileRepository.saveAll(images);
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