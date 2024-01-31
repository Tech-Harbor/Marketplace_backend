package com.example.backend.web.File;


import com.example.backend.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;
    private final FileUpload fileUpload;

    private static final String URL_iMAGES = "/images";
    private static final String URL_UPLOAD = "/upload";

    @GetMapping(URL_iMAGES)
    public List<ImageDTO> getAllImage(){
        return imageService.getAllPhoto();
    }

    @SneakyThrows
    @PostMapping(URL_UPLOAD)
    @ResponseBody
    public ImageDTO upload(@RequestParam MultipartFile file){
        BufferedImage bi = ImageIO.read(file.getInputStream());

        if (bi == null) {
            throw new BadRequestException("There is no uploaded image");
        }

        Map result = fileUpload.uploadFile(file);
        ImageEntity image = new ImageEntity(
                (String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id")
        );

        return imageService.save(image);
    }
}