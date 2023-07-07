package com.jvel.edify.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("edify-jwt")
public record JwtProperties(
        String SECRET_KEY,
        String ISSUER,
        String AUDIENCE) {
}
