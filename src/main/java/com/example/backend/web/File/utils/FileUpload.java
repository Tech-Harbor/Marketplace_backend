package com.example.backend.web.File.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileUpload {
    Map uploadFile(MultipartFile multipartFile);
    Map deleteById(String id);
}
