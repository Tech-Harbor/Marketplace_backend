package com.example.backend.web.Advertisement;

import com.example.backend.web.Advertisement.store.AdvertisementEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long> {
    AdvertisementEntity getByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE AdvertisementEntity ad "
            + "SET ad.createDate = :now WHERE ad.updateActiveDate <= :updateDate AND ad.active = false")
    void updateActiveAdvertisements(@Param("now") LocalDateTime now,
                                    @Param("updateDate") LocalDateTime updateDate);

    //TODO Поямнення updateActiveAdvertisements!
    // 01.08.2022 створення без true, а стоїть false
    // 01.09.2022 треба обновити дату всі які є на false
    // 01.10.2022 видалиться через місяць, якщо не активують оголошення deleteActiveAdvertisements

    @Transactional
    @Modifying
    @Query("DELETE FROM AdvertisementEntity ad WHERE ad.updateActiveDate <= :dateTime AND ad.active = false")
    void deleteActiveAdvertisements(@Param("dateTime") LocalDateTime dateTime);
}