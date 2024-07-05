package com.example.backend.websocket.service;

import com.example.backend.websocket.store.entities.ReceivedMessageEntity;
import com.example.backend.websocket.store.entities.SendMessageEntity;

public interface MessageService {
    SendMessageEntity sendToTopic(ReceivedMessageEntity receivedMessage, String username);
    SendMessageEntity sendToUser(ReceivedMessageEntity receivedMessage, String username);
}