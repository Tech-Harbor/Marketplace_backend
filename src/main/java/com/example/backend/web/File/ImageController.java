package com.example.backend.web.File;


import com.example.backend.utils.annotations.ApiResponseCreated;
import com.example.backend.utils.annotations.ApiResponseDelete;
import com.example.backend.utils.annotations.ApiResponseOK;
import com.example.backend.web.File.store.dto.ImageCreateDTO;
import com.example.backend.web.File.store.dto.ImageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Tag(name = "ImageService for admin")
@RequestMapping("/api/admin")
public class ImageController {

    private final ImageServer imageServer;
    private static final String URI_IMAGES_ID = "/{imageId}";
    private static final String URI_IMAGE = "/upload";
    private static final String URI_IMAGES = "/images";

    @ApiResponseOK
    @GetMapping(value = URI_IMAGES, produces = APPLICATION_JSON_VALUE)
    public List<ImageDTO> getAllImage() {
        return imageServer.getAllPhoto();
    }

    @Operation(summary = "Create picture")
    @ResponseBody
    @ApiResponseCreated
    @PostMapping(value = URI_IMAGE, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ImageCreateDTO upload(@RequestParam final MultipartFile file) {
        return imageServer.uploadImage(file);
    }

    @Operation(summary = "Delete picture by name")
    @ResponseBody
    @ApiResponseDelete
    @GetMapping(value = URI_IMAGES_ID, produces = APPLICATION_JSON_VALUE)
    public void deleteCloudinaryAndRepositoryById(@PathVariable final String imageId) {
        imageServer.imageDeleteId(imageId);
    }
}