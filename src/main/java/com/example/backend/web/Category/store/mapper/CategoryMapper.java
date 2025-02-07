package com.example.backend.web.Category.store.mapper;

import com.example.backend.utils.mapper.AdvertisementBaseMapper;
import com.example.backend.utils.mapper.BaseMapper;
import com.example.backend.web.Category.store.CategoryEntity;
import com.example.backend.web.Category.store.dto.CategoryCreateDTO;
import com.example.backend.web.Category.store.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BaseMapper.class, AdvertisementBaseMapper.class})
public interface CategoryMapper {
    @Mapping(target = "image", qualifiedByName = {"BaseMapper", "getImageUrl"})
    CategoryCreateDTO categoryMapperCreateDTO(CategoryEntity category);
    @Mapping(target = "image", qualifiedByName = {"BaseMapper", "getImageUrl"})
    @Mapping(target = "advertisements", qualifiedByName = {"AdvertisementBaseMapper", "getAllAdvertisements"})
    CategoryDTO categoryDTO(CategoryEntity category);
}