package com.example.backend.web.User;

import com.example.backend.web.User.store.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserEntity user WHERE user.createData <= :dateTime AND user.enabled = false")
    void deleteNotValidatedUsers(@Param("dateTime") LocalDateTime dateTime);

    Optional<UserEntity> getByFirstname(String firstName);
}