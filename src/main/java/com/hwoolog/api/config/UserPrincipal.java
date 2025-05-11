package com.hwoolog.api.config;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserPrincipal extends User {

    private final Long userId;

    public UserPrincipal(com.hwoolog.api.domain.User user) {
        super(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ADMIN")));
        this.userId = user.getId();
    }
}