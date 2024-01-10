package com.example.backend.ImageFile;

import com.example.backend.ImageFile.ImageFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFileEntity, Long> {

}