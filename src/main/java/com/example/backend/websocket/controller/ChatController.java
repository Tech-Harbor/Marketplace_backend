package com.example.backend.websocket.controller;

import com.example.backend.security.service.details.MyUserDetails;
import com.example.backend.websocket.models.ReceivedMessage;
import com.example.backend.websocket.models.SendMessage;
import com.example.backend.websocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;

    private static final String MESSAGE_APPLICATION= "/application";
    private static final String SEND_TOPIC = "/topic";
    private static final String MESSAGE_PRIVATE= "/private";

    @MessageMapping(MESSAGE_APPLICATION)
    @SendTo(SEND_TOPIC)
    public SendMessage sendToTopic(final ReceivedMessage receivedMessage, final MyUserDetails myUserDetails) {
        return messageService.sendToTopic(receivedMessage, myUserDetails.getUsername());
    }

    @MessageMapping(MESSAGE_PRIVATE)
    public SendMessage sendToUser(final @Payload ReceivedMessage receivedMessage, final MyUserDetails myUserDetails) {
        return messageService.sendToUser(receivedMessage, myUserDetails.getUsername());
    }


}
