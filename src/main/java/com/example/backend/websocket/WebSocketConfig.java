package com.example.backend.websocket;

import com.example.backend.utils.props.WebSocketProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketProperties webSocketProperties;

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(webSocketProperties.getDestPrefixes());
        registry.setApplicationDestinationPrefixes(webSocketProperties.getAppPrefix());
        registry.setUserDestinationPrefix(webSocketProperties.getUserPrefix());
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint(webSocketProperties.getEndpoint());
        registry.addEndpoint(webSocketProperties.getEndpoint()).setAllowedOriginPatterns("*").withSockJS();
    }
}