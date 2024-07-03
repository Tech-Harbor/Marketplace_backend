package com.example.backend.utils.general;

import com.example.backend.web.Advertisement.AdvertisementRepository;
import com.example.backend.web.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class SchedulerProject {

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

    @Scheduled(fixedRate = 3600000)
    public void deleteUsers() {
        userRepository.deleteNotValidatedUsers(LocalDateTime.now().minusHours(1));
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void deleteActiveAdvertisement() {
        advertisementRepository.deleteActiveAdvertisements(LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateActiveAdvertisement() {
        advertisementRepository.updateActiveAdvertisements(LocalDateTime.now(), LocalDateTime.now());
    }
}