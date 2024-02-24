package com.example.backend.web.File;

import com.example.backend.web.File.utils.FileUpload;
import com.example.backend.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;
    private final FileUpload fileUpload;
    private final ImageFactory imageFactory;

    @Override
    @SneakyThrows
    public ImageDTO uploadImage(MultipartFile file){
        Optional<BufferedImage> imageOptional = Optional.ofNullable(ImageIO.read(file.getInputStream()));

        imageOptional.orElseThrow(() -> new BadRequestException("There is no uploaded image"));


        Map result = fileUpload.uploadFile(file);

        ImageEntity image = new ImageEntity(
                (String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id")
        );

        return imageFactory.makeImageFactory(imageRepository.save(image));
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