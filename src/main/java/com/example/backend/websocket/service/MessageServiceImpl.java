package com.example.backend.websocket.service;

import com.example.backend.websocket.store.entities.ReceivedMessageEntity;
import com.example.backend.websocket.store.repository.ReceivedMessageRepository;
import com.example.backend.websocket.store.entities.SendMessageEntity;
import com.example.backend.websocket.store.repository.SendMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class MessageServiceImpl implements MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ReceivedMessageRepository receivedMessageRepository;
    private final SendMessageRepository sendMessageRepository;

    @Override
    public SendMessageEntity sendToTopic(final ReceivedMessageEntity receivedMessage, final String username) {

        final var messageTopic = ReceivedMessageEntity.builder()
                .sentFrom(username)
                .sendTo("topic")
                .text(receivedMessage.getText())
                .build();

        receivedMessageRepository.save(messageTopic);

        final var build = SendMessageEntity.builder()
                .receivedMessage(messageTopic)
                .createData(LocalDateTime.now())
                .build();

        return sendMessageRepository.save(build);
    }

    @Override
    public SendMessageEntity sendToUser(final ReceivedMessageEntity receivedMessage, final String username) {

        final var sendTo = receivedMessage.getSendTo();

        final var messageUser = ReceivedMessageEntity.builder()
                .sendTo(username)
                .text(receivedMessage.getText())
                .build();

        receivedMessageRepository.save(messageUser);

        final var sendUser = SendMessageEntity.builder()
                .receivedMessage(messageUser)
                .createData(LocalDateTime.now())
                .build();

        simpMessagingTemplate.convertAndSendToUser(sendTo, "/topic", sendUser);
        return sendMessageRepository.save(sendUser);
    }
}
