package com.hwoolog.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Signup {

    private String name;
    private String email;
    private String password;

    @Builder
    public Signup(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
