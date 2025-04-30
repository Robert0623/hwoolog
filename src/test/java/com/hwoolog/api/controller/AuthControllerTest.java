package com.hwoolog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwoolog.api.repository.UserRepository;
import com.hwoolog.api.request.Login;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("db에서 유저 정보를 가져와서 로그인 인증")
    void test1() throws Exception {
        String email = "aaa@aaa.com";
        String password = "1234";
        String name = "혀누";

        Login login = new Login();
        login.setEmail(email);
        login.setPassword(password);

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password))
                .andExpect(jsonPath("$.name").value(name))
                .andDo(print());

    }
}