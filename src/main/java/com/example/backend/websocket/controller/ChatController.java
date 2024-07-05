package com.example.backend.websocket.controller;

import com.example.backend.websocket.service.MessageService;
import com.example.backend.websocket.store.entities.ReceivedMessageEntity;
import com.example.backend.websocket.store.entities.SendMessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;

    private static final String MESSAGE_APPLICATION= "/application";
    private static final String SEND_TOPIC = "/topic";
    private static final String MESSAGE_PRIVATE= "/private";

    @MessageMapping(MESSAGE_APPLICATION)
    @SendTo(SEND_TOPIC)
    public SendMessageEntity sendToTopic(final @Payload ReceivedMessageEntity receivedMessage,
                                         final @Payload String username) {
        return messageService.sendToTopic(receivedMessage, username);
    }

    @MessageMapping(MESSAGE_PRIVATE)
    public SendMessageEntity sendToUser(final @Payload ReceivedMessageEntity receivedMessage,
                                        final @Payload String username) {
        return messageService.sendToUser(receivedMessage, username);
    }
}