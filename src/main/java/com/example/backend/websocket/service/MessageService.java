package com.example.backend.websocket.service;

import com.example.backend.websocket.models.ReceivedMessage;
import com.example.backend.websocket.models.SendMessage;

public interface MessageService {
    SendMessage sendToTopic(ReceivedMessage receivedMessage, String username);
    SendMessage sendToUser(ReceivedMessage receivedMessage, String username);
}
