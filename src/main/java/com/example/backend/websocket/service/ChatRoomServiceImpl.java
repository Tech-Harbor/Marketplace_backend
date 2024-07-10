package com.example.backend.websocket.service;

import com.example.backend.websocket.models.ChatRoomEntity;
import com.example.backend.websocket.models.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Optional<String> getChatRoomId(final String senderId,
                                          final String recipientId,
                                          final boolean createNewRoomIfNotExists) {
        return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoomEntity::getChatId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        return Optional.of(createChatId(senderId, recipientId));
                    }
                    return  Optional.empty();
                }
        );
    }

    private String createChatId(final String senderId, final String recipientId) {
        final var chatId = String.format("%s_%s", senderId, recipientId);

        final var senderRecipient = ChatRoomEntity.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        final var recipientSender = ChatRoomEntity.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return chatId;
    }
}
