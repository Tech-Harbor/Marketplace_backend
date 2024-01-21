package com.example.backend.File;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileUploadController {

    private final FileUpload fileUpload;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String url = fileUpload.uploadFile(file);
        return "File uploaded successfully: File path :  " + url;
    }
}