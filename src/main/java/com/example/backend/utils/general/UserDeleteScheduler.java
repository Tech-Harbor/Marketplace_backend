package com.example.backend.utils.general;

import com.example.backend.web.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class UserDeleteScheduler {

    private UserRepository userRepository;

    @Scheduled(fixedRate = 3600000)
    public void deleteUsers() {
        userRepository.deleteNotValidatedUsers(LocalDateTime.now().minusHours(1));
    }
}