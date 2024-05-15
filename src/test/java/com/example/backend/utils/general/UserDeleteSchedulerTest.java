package com.example.backend.utils.general;

import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserDeleteSchedulerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDeleteScheduler scheduler;

    @Test
    @Transactional
    void deleteUsersTest() {
        final var user = UserEntity.builder()
                .createData(LocalDateTime.now().minusHours(1))
                .enabled(false)
                .build();

        userRepository.save(user);

        scheduler.deleteUsers();

        assertTrue(userRepository.findAll().isEmpty());
    }
}