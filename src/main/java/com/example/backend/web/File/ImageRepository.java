package com.example.backend.web.File;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    ImageDTO getReferenceById(Long id);
    ImageEntity getByImageUrl(String image);
}