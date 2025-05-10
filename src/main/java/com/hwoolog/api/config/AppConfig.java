package com.hwoolog.api.config;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;
import java.util.Base64;

// @Configuration
@Getter
@ConfigurationProperties(prefix = "hwoolog")
public class AppConfig {

    private byte[] jwtKey;

    private SecretKey jwtSecretKey;

    public void setJwtKey(String jwtKey) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
        this.jwtSecretKey = Keys.hmacShaKeyFor(this.jwtKey);
    }
}
