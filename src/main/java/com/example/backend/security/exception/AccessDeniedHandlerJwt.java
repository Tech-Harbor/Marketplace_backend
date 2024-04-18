package com.example.backend.security.exception;

import com.example.backend.security.models.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedHandlerJwt implements AccessDeniedHandler {
    @Override
    @SneakyThrows
    public void handle(final HttpServletRequest httpServletRequest,
                       final HttpServletResponse httpServletResponse,
                       final AccessDeniedException e) {

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpServletResponse.SC_FORBIDDEN)
                .message("You don't have required role to perform this action.")
                .build();

        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(httpServletResponse.getOutputStream(), response);
    }
}