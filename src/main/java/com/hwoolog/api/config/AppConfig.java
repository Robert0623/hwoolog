package com.hwoolog.api.config;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.List;
import java.util.Map;

// @Configuration
@Getter
@ConfigurationProperties(prefix = "hwoolog")
public class AppConfig {

    private byte[] jwtKey;

    private SecretKey jwtSecreKey;

    public void setJwtKey(String jwtKey) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
        this.jwtSecreKey = Keys.hmacShaKeyFor(this.jwtKey);
    }
}
