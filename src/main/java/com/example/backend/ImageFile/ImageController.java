package com.example.backend.ImageFile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

    private final ImageFileServiceImpl imageService;
    private static final String URI_IMAGES = "/images";
    private static final String URI_IMAGE_ID = "/images/{id}";

    @PostMapping(URI_IMAGES)
    @ResponseStatus(HttpStatus.CREATED)
    public ImageFileDTO create(@ModelAttribute MultipartFile image) {
        return imageService.uploadImage(image);
    }

    @GetMapping(URI_IMAGE_ID)
    public ImageFileDTO getImageByID(@PathVariable Long id){
        return imageService.getImageFileById(id);
    }

    @DeleteMapping(URI_IMAGE_ID)
    public void deleteIdImage(@PathVariable Long id){
        imageService.deleteIdImage(id);
    }
}
