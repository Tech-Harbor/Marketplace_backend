package com.example.backend.web.Advertisement;

import com.example.backend.web.Advertisement.store.AdvertisementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long> {
    AdvertisementEntity getByName(String name);
}