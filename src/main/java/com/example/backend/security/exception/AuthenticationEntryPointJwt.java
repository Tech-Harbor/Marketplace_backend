package com.example.backend.security.exception;

import com.example.backend.security.models.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    @SneakyThrows
    public void commence(final HttpServletRequest httpServletRequest,
                         final HttpServletResponse httpServletResponse,
                         final AuthenticationException authenticationException) {

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse response = new ErrorResponse(
                HttpServletResponse.SC_UNAUTHORIZED,
                "You need to login first in order to perform this action."
        );

        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(httpServletResponse.getOutputStream(), response);
    }
}
