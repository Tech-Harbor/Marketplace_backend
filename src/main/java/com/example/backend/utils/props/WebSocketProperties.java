package com.example.backend.utils.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.socket")
public class WebSocketProperties {
    private String[] destPrefixes;
    private String appPrefix;
    private String endpoint;
    private String topicPrefix;
}