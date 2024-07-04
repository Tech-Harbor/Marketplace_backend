package com.example.backend.websocket.service;

import com.example.backend.websocket.models.ReceivedMessage;
import com.example.backend.websocket.models.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class MessageServiceImpl implements MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public SendMessage sendToTopic(final ReceivedMessage receivedMessage, final String username) {

        final var messageTopic = ReceivedMessage.builder()
                .sentFrom(username)
                .sendTo("topic")
                .build();

        return SendMessage.builder()
                .receivedMessage(messageTopic)
                .localDateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public SendMessage sendToUser(final ReceivedMessage receivedMessage, final String username) {

        final var sendTo = receivedMessage.sendTo();

        final var messageUser = ReceivedMessage.builder()
                .sendTo(username)
                .build();

        final var sendUser = SendMessage.builder()
                .receivedMessage(messageUser)
                .localDateTime(LocalDateTime.now())
                .build();

        simpMessagingTemplate.convertAndSendToUser(sendTo, "/topic", sendUser);
        return sendUser;
    }
}
