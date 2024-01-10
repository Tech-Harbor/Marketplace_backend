package com.example.backend.utils.uploadImage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {
    public static final String PATH_TO_UPLOAD = System.getProperty("user.dir") + "\\uploads\\";

    @Override
    public boolean saveImage(MultipartFile file, String filename) {
        try {
            create_file_image();
            file.transferTo(new File(PATH_TO_UPLOAD + filename));
            return true;
        }   catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public String getUniqueFilename(String originalFilename) {
        try {
            return UUID.randomUUID() + "." + originalFilename.split("\\.", 2)[1];
        }   catch (IndexOutOfBoundsException ex) {
            return UUID.randomUUID() + ".jpg";
        }
    }

    private void create_file_image(){
        File folderUpload = new File(PATH_TO_UPLOAD);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
    }
}
