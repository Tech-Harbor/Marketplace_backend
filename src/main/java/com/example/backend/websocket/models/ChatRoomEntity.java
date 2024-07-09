package com.example.backend.websocket.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chatRoom")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatId;
    private String senderId;
    private String recipientId;
}