package com.example.backend.web.File;


import com.example.backend.web.File.utils.FileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private static final String URI_IMAGES_ID = "/{id}";

    private final ImageService imageService;
    private final FileUpload fileUpload;

    @GetMapping
    public List<ImageDTO> getAllImage(){
        return imageService.getAllPhoto();
    }

    @PostMapping
    @ResponseBody
    public ImageDTO upload(@RequestParam MultipartFile file){
        return imageService.uploadImage(file);
    }

    @GetMapping(URI_IMAGES_ID)
    @ResponseBody
    public ImageDTO imageGetById(@RequestParam Long imageId) {
        return imageService.imageById(imageId);
    }
}