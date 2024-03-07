package com.example.backend.web.exception;

import java.time.LocalDateTime;

public record ErrorResponseDTO(LocalDateTime timeStamp, String error, int status, String message, String path) { }