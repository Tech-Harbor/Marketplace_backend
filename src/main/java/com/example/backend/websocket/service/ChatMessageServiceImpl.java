package com.example.backend.websocket.service;

import com.example.backend.websocket.models.ChatMessageEntity;
import com.example.backend.websocket.models.ChatMessageRepository;
import com.example.backend.websocket.models.ChatNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.backend.utils.exception.RequestException.badRequestException;

@Component
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    @Override
    public void processMessage(final ChatMessageEntity chatMessage) {
        final var savedMsg = save(chatMessage);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/queue/messages",

                new ChatNotification(savedMsg.getSenderId(), savedMsg.getRecipientId(), savedMsg.getContent())
        );
    }

    @Override
    public List<ChatMessageEntity> findChatMessages(final String senderId, final String recipientId) {
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
    }

    private ChatMessageEntity save(final ChatMessageEntity chatMessage) {
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow(
                        () -> badRequestException("chat does not exist")
                );

        chatMessage.setChatId(chatId);

        return repository.save(chatMessage);
    }
}
