package com.example.backend.web.File.store.mapper;

import com.example.backend.web.File.store.ImageEntity;
import com.example.backend.web.File.store.dto.ImageCreateDTO;
import com.example.backend.web.File.store.dto.ImageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDTO imageMapperDTO(ImageEntity image);
    ImageCreateDTO imageMapperCreateDTO(ImageEntity image);
}