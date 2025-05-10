package com.hwoolog.api.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

// @Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "hwoo")
public class AppConfig {

//    public static final String KEY = "YzgxNzk1MzAtOTY0Zi00ZDM4LWJlNzgtZTJiNWFlNWJhZWM4";
    public Hello hello;

    @Getter
    @Setter
    public static class Hello {
        public String name;
        public String address;
        public Long zip;
    }
}
