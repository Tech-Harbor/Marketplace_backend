package com.example.backend.websocket.controller;

import com.example.backend.websocket.models.ChatMessageEntity;
import com.example.backend.websocket.servers.ChatMessageServer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageServer chatMessageServer;

    @MessageMapping("/chat")
    public void processMessage(final @Payload ChatMessageEntity chatMessage) {
        chatMessageServer.processMessage(chatMessage);
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public List<ChatMessageEntity> findChatMessages(final @PathVariable String senderId,
                                                    final @PathVariable String recipientId) {
        return chatMessageServer.findChatMessages(senderId, recipientId);
    }
}