package com.example.backend.web.File.upload;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileUpload {
    Map uploadFile(MultipartFile multipartFile);
    Map deleteCloudinaryById(String id);
}
