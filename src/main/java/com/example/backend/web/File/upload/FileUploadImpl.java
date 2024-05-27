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
        File file = convertMultiPartToFile(multipartFile);

        Map map = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

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
        final File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}