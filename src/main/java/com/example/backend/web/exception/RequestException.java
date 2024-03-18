package com.example.backend.web.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RequestException extends RuntimeException {
    private HttpStatus status;

    public RequestException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static RequestException badRequestException(final String message) {
        return new RequestException(message, HttpStatus.BAD_REQUEST);
    }

    public static RequestException notFoundRequestException(final String message) {
        return new RequestException(message, HttpStatus.NOT_FOUND);
    }
}