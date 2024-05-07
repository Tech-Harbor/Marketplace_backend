package com.example.backend.web.Advertisement;

import java.util.List;

public interface AdvertisementService {
    AdvertisementDTO createAdvertisement(Long id, AdvertisementDTO entity);
    List<AdvertisementDTO> getAllAdvertisement();
    AdvertisementDTO getOneAdvertisement(Long id);
    AdvertisementDTO editAdvertisement(Long id, AdvertisementDTO entity);
    void deleteIdAdvertisement(Long id);
    List<AdvertisementEntity> getFilterAdvertisementName(String name);
    void deleteAll();
}