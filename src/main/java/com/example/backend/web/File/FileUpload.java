package com.example.backend.web.File;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface FileUpload {
    Map uploadFile(MultipartFile multipartFile) throws IOException;
    Map deleteById(String id);
}
