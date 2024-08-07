package com.example.backend.web.Advertisement.store.mapper;

import com.example.backend.web.Advertisement.store.AdvertisementEntity;
import com.example.backend.web.Advertisement.store.dto.AdvertisementCreateDTO;
import com.example.backend.web.Advertisement.store.dto.AdvertisementDTO;
import com.example.backend.web.Advertisement.store.dto.AdvertisementUpdateDTO;
import com.example.backend.utils.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BaseMapper.class})
public interface AdvertisementMapper {
    @Mapping(target = "images", qualifiedByName = {"BaseMapper", "getAllImages"})
    @Mapping(target = "category", qualifiedByName = {"BaseMapper", "getCategoryName"})
    AdvertisementCreateDTO advertisementMapperCreateDTO(AdvertisementEntity entity);
    @Mapping(target = "category", qualifiedByName = {"BaseMapper", "getCategoryName"})
    AdvertisementUpdateDTO advertisementMapperUpdateDTO(AdvertisementEntity entity);
    @Mapping(target = "images", qualifiedByName = {"BaseMapper", "getAllImages"})
    @Mapping(target = "category", qualifiedByName = {"BaseMapper", "getCategoryName"})
    AdvertisementDTO advertisementMapperDTO(AdvertisementEntity entity);
}