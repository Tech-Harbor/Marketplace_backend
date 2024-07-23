package com.example.backend.websocket.servers;

import java.util.Optional;

public interface ChatRoomServer {
    Optional<String> getChatRoomId(String senderId, String recipientId, boolean createNewRoomIfNotExists);
}