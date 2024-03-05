package com.example.backend.web.File;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;
    private static final String URL_iMAGES = "/images";
    private static final String URL_UPLOAD = "/upload";
    private static final String URL_IMAGE_ID = "/image";

    @GetMapping(URL_iMAGES)
    public List<ImageDTO> getAllImage(){
        return imageService.getAllPhoto();
    }

    @PostMapping(URL_UPLOAD)
    @ResponseBody
    public ImageDTO upload(@RequestParam MultipartFile file){
        return imageService.uploadImage(file);
    }

    @GetMapping(URL_IMAGE_ID)
    @ResponseBody
    public ImageDTO imageGetById(@RequestParam Long imageId){
        return imageService.imageById(imageId);
    }
}