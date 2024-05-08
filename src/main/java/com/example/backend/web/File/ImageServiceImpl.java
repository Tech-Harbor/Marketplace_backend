package com.example.backend.web.File;

import com.example.backend.web.File.upload.FileUpload;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.backend.utils.exception.RequestException.badRequestException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageFactory imageFactory;
    private final FileUpload fileUpload;

    @Override
    @SneakyThrows
    @Transactional
    public ImageDTO uploadImage(final MultipartFile file) {
        final var imageOptional = Optional.ofNullable(ImageIO.read(file.getInputStream()));

        final var result = fileUpload.uploadFile(file);

        final var image = new ImageEntity(
                (String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id")
        );

        imageOptional.orElseThrow(() -> badRequestException("There is no uploaded image"));

        return imageFactory.apply(imageRepository.save(image));
    }

    @Override
    public List<ImageDTO> getAllPhoto() {
        return imageRepository.findAll().stream()
                .map(imageFactory)
                .collect(Collectors.toList());
    }

    @Override
    public ImageDTO imageById(final Long id) {
        return imageRepository.getReferenceById(id);
    }
}