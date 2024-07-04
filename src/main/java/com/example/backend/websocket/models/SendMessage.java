package com.example.backend.websocket.models;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SendMessage(ReceivedMessage receivedMessage, LocalDateTime localDateTime) { }