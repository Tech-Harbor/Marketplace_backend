package com.example.backend.websocket.listener;

import com.example.backend.web.User.store.dto.UserWebSocketDTO;
import com.example.backend.websocket.models.ChatMessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

import static com.example.backend.websocket.models.MessageType.LEAVE;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(final SessionDisconnectEvent event,
                                                  final UserWebSocketDTO userWebSocketDTO) {
        final var headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        final var username = (String) Objects.requireNonNull(
                headerAccessor.getSessionAttributes()).get(userWebSocketDTO.lastname()
        );

        if (username != null) {
            final var chatMessage = ChatMessageEntity.builder()
                    .type(LEAVE)
                    .senderId(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
