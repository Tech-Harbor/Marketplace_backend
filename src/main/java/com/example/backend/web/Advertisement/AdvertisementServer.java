package com.example.backend.web.Advertisement;

import com.example.backend.web.Advertisement.store.dto.AdvertisementCreateDTO;
import com.example.backend.web.Advertisement.store.dto.AdvertisementDTO;
import com.example.backend.web.Advertisement.store.dto.AdvertisementUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdvertisementServer {
    AdvertisementCreateDTO createAdvertisement(String jwt, AdvertisementCreateDTO entity, List<MultipartFile> files);
    List<AdvertisementDTO> getAllAdvertisement();
    AdvertisementDTO advertisement(String jwt);
    AdvertisementUpdateDTO editAdvertisement(String jwt, AdvertisementUpdateDTO entity);
    void deleteAdvertisement(String jwt);
    void deleteAll(String jwt);
}