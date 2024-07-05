package com.example.backend.websocket.store.repository;

import com.example.backend.websocket.store.entities.ReceivedMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceivedMessageRepository extends JpaRepository<ReceivedMessageEntity, Long> { }