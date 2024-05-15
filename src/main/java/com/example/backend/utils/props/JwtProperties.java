package com.example.backend.utils.props;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String key;
    private Duration jwtAccessExpiration;
    private Duration jwtRefreshExpiration;
    private Duration jwtUserDataExpiration;
}