package com.example.backend.web.File;


import com.example.backend.web.File.store.dto.ImageCreateDTO;
import com.example.backend.web.File.store.dto.ImageDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "ImageService")
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;
    private static final String URI_IMAGES_ID = "/{imageId}";
    private static final String URI_IMAGE = "/upload";
    private static final String URI_IMAGES = "/images";

    @GetMapping(URI_IMAGES)
    public List<ImageDTO> getAllImage() {
        return imageService.getAllPhoto();
    }

    @PostMapping(URI_IMAGE)
    @ResponseBody
    public ImageCreateDTO upload(@RequestParam final MultipartFile file) {
        return imageService.uploadImage(file);
    }

    @GetMapping(URI_IMAGES_ID)
    @ResponseBody
    public ImageDTO imageGetById(@PathVariable final Long imageId) {
        return imageService.imageById(imageId);
    }
}