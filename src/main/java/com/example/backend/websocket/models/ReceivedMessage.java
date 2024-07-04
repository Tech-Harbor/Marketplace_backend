package com.example.backend.websocket.models;


import lombok.Builder;

@Builder
public record ReceivedMessage(String sentFrom, String sendTo, String text) { }