package com.example.backend.web.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RequestException extends RuntimeException{
    private HttpStatus status;

    public RequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static RequestException badRequestException(String message){
        return new RequestException(message, HttpStatus.BAD_REQUEST);
    }
}
