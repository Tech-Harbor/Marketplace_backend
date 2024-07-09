package com.example.backend.websocket.controller;

import com.example.backend.websocket.models.ChatMessageEntity;
import com.example.backend.websocket.service.ChatMessageService;
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

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(final @Payload ChatMessageEntity chatMessage) {
        chatMessageService.processMessage(chatMessage);
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public List<ChatMessageEntity> findChatMessages(final @PathVariable String senderId,
                                                    final @PathVariable String recipientId) {
        return chatMessageService.findChatMessages(senderId, recipientId);
    }
}