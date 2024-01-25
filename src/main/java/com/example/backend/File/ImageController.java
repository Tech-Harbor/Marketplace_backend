package com.example.backend.File;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ImageEntity>> list(){
        List<ImageEntity> list = imageService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(URL_UPLOAD)
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam MultipartFile file){
        BufferedImage bi = ImageIO.read(file.getInputStream());
        if (bi == null) {
            return new ResponseEntity<>("Image non valide!", HttpStatus.BAD_REQUEST);
        }
        Map result = fileUpload.uploadFile(file);
        ImageEntity image = new ImageEntity(
                (String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id")
        );

        imageService.save(image);

        return new ResponseEntity<>("image ajoutée avec succès ! ", HttpStatus.OK);
    }
}
