package com.example.backend.websocket.service;

import com.example.backend.websocket.models.ChatMessageEntity;

import java.util.List;

public interface ChatMessageService {
    void processMessage(ChatMessageEntity chatMessage);
    List<ChatMessageEntity> findChatMessages(String senderId, String recipientId);
}