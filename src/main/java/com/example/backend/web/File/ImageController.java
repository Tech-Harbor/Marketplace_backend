package com.example.backend.web.File;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private static final String URI_IMAGES_ID = "/{imageId}";

    @GetMapping
    public List<ImageDTO> getAllImage() {
        return imageService.getAllPhoto();
    }

    @PostMapping
    @ResponseBody
    public ImageDTO upload(@RequestParam final MultipartFile file) {
        return imageService.uploadImage(file);
    }

    @GetMapping(URI_IMAGES_ID)
    @ResponseBody
    public ImageDTO imageGetById(@PathVariable final Long imageId) {
        return imageService.imageById(imageId);
    }
}