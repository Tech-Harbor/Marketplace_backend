package com.example.backend.api.controllers;

import com.example.backend.api.DTO.ImageFileDTO;
import com.example.backend.api.services.impl.ImageFileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ImageController {

    private final ImageFileServiceImpl imageService;
    private static final String CREATE_IMAGE = "/image";
    private static final String GET_IMAGE_BY_ID = "/{id}";
    private static final String DELETE_IMAGE_ID = "/delete/image/{id}";

    @PostMapping(CREATE_IMAGE)
    @ResponseStatus(HttpStatus.CREATED)
    public ImageFileDTO create(@ModelAttribute MultipartFile image) {
        return imageService.uploadImage(image);
    }

    @GetMapping(GET_IMAGE_BY_ID)
    public ImageFileDTO getImageByID(@PathVariable Long id){
        return imageService.getImageFileById(id);
    }

    @DeleteMapping(DELETE_IMAGE_ID)
    public void deleteIdImage(@PathVariable Long id){
        imageService.deleteIdImage(id);
    }
}
