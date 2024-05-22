package com.example.backend.web.Advertisement;

import java.util.List;

public interface AdvertisementService {
    AdvertisementDTO createAdvertisement(String jwt, AdvertisementDTO entity);
    List<AdvertisementDTO> getAllAdvertisement();
    AdvertisementDTO advertisement(String jwt);
    AdvertisementDTO editAdvertisement(String jwt, AdvertisementDTO entity);
    void deleteAdvertisement(String jwt);
    void deleteAll();
}