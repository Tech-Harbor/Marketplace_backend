package com.example.backend.web.Advertisement;

import com.example.backend.web.Advertisement.store.dto.AdvertisementCreateDTO;
import com.example.backend.web.Advertisement.store.dto.AdvertisementDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdvertisementService {
    AdvertisementCreateDTO createAdvertisement(String jwt, AdvertisementCreateDTO entity, List<MultipartFile> files);
    List<AdvertisementDTO> getAllAdvertisement();
    AdvertisementDTO advertisement(String jwt);
    AdvertisementDTO editAdvertisement(String jwt, AdvertisementDTO entity);
    void deleteAdvertisement(String jwt);
    void deleteAll();
}