package com.example.backend.websocket.store.repository;

import com.example.backend.websocket.store.entities.SendMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendMessageRepository extends JpaRepository<SendMessageEntity, Long> { }