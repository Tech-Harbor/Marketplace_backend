package com.example.backend.store.repository;

import com.example.backend.store.models.ImageFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFileEntity, Long> {

}