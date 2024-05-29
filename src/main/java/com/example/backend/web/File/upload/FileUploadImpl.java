package com.example.backend.web.File.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileUploadImpl implements FileUpload {

    private final Cloudinary cloudinary;

    @SneakyThrows
    @Override
    public Map uploadFile(final MultipartFile multipartFile) {
        final var file = convertMultiPartToFile(multipartFile);
        final var map = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

        if (!Files.deleteIfExists(file.toPath())) {
            throw new IOException("Failed to deleteCategory temporary file: " + file.getAbsolutePath());
        }

        return map;
    }

    @SneakyThrows
    @Override
    public Map deleteById(final String id) {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    @SneakyThrows
    private File convertMultiPartToFile(final MultipartFile file) {
        final var convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        final var fileOutputStream = new FileOutputStream(convFile);

        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        return convFile;
    }
}