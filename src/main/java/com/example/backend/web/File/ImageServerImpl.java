package com.example.backend.web.File;

import com.example.backend.web.File.store.ImageEntity;
import com.example.backend.web.File.store.dto.ImageCreateDTO;
import com.example.backend.web.File.store.dto.ImageDTO;
import com.example.backend.web.File.store.factory.ImageCreateFactory;
import com.example.backend.web.File.store.factory.ImageFactory;
import com.example.backend.web.File.upload.FileUpload;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.util.List;
import java.util.Optional;

import static com.example.backend.utils.exception.RequestException.badRequestException;

@Service
@RequiredArgsConstructor
public class ImageServerImpl implements ImageServer {

    private final ImageCreateFactory imageCreateFactory;
    private final ImageRepository imageRepository;
    private final ImageFactory imageFactory;
    private final FileUpload fileUpload;

    @Override
    @SneakyThrows
    @Transactional
    public ImageCreateDTO uploadImage(final MultipartFile file) {
        final var imageOptional = Optional.ofNullable(ImageIO.read(file.getInputStream()));

        final var result = fileUpload.uploadFile(file);

        final var image = ImageEntity.builder()
                .name((String) result.get("original_filename"))
                .imageUrl((String) result.get("url"))
                .imageId((String) result.get("public_id"))
                .build();

        imageOptional.orElseThrow(() -> badRequestException("There is no uploaded image"));

        return imageCreateFactory.apply(imageRepository.save(image));
    }

    @Override
    @SneakyThrows
    @Transactional
    public ImageEntity uploadImageEntity(final MultipartFile file) {
        final var imageOptional = Optional.ofNullable(ImageIO.read(file.getInputStream()));

        imageOptional.orElseThrow(() -> badRequestException("There is no uploaded image"));

        final var map = fileUpload.uploadFile(file);

        final var image = ImageEntity.builder()
                .name((String) map.get("original_filename"))
                .imageUrl((String) map.get("url"))
                .imageId((String) map.get("public_id"))
                .build();

        return imageRepository.save(image);
    }

    @Override
    public List<ImageDTO> getAllPhoto() {
        return imageRepository.findAll().stream()
                .map(imageFactory)
                .toList();
    }

    @Override
    public void imageDeleteId(final String id) {
        fileUpload.deleteCloudinaryById(id);
        imageRepository.deleteById(Long.valueOf(id));
    }
}