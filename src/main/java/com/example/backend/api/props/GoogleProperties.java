package com.example.backend.api.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "security.oauth2.client.registration")
public class GoogleProperties {
    private String clientId;
    private String clientSecret;
}
