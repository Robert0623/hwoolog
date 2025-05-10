package com.hwoolog;

import com.hwoolog.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class HwoologApplication {

    public static void main(String[] args) {
        SpringApplication.run(HwoologApplication.class, args);
    }

}
