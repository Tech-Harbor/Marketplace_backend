package com.example.backend.websocket.servers;

import com.example.backend.websocket.models.ChatMessageEntity;

import java.util.List;

public interface ChatMessageServer {
    void processMessage(ChatMessageEntity chatMessage);
    List<ChatMessageEntity> findChatMessages(String senderId, String recipientId);
}