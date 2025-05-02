package com.hwoolog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwoolog.api.domain.Session;
import com.hwoolog.api.domain.User;
import com.hwoolog.api.repository.SessionRepository;
import com.hwoolog.api.repository.UserRepository;
import com.hwoolog.api.request.Login;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void test1() throws Exception {
        // given
        final String EMAIL = "aaa@aaa.com";
        final String PASSWORD = "1234";
        final String NAME = "hwoo";

        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        // scrypt, bcrypt

        userRepository.save(user);

        Login login = Login.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공 후 세션 1개 생성")
    @Transactional
    void test2() throws Exception {
        // given
        final String EMAIL = "aaa@aaa.com";
        final String PASSWORD = "1234";
        final String NAME = "hwoo";

        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        // scrypt, bcrypt

       userRepository.save(user);

        Login login = Login.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        Assertions.assertEquals(1L, user.getSessions().size());
    }

    @Test
    @DisplayName("로그인 성공 후 세션 응답")
    void test3() throws Exception {
        // given
        final String EMAIL = "aaa@aaa.com";
        final String PASSWORD = "1234";
        final String NAME = "hwoo";

        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        // scrypt, bcrypt

        userRepository.save(user);

        Login login = Login.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", Matchers.notNullValue()))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공 후 권한이 필요한 페이지에 접속한다. /foo")
    void test4() throws Exception {
        // given
        final String EMAIL = "aaa@aaa.com";
        final String PASSWORD = "1234";
        final String NAME = "hwoo";

        User user = User.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        Session session = user.addSession();

        userRepository.save(user);

        // expected
        mockMvc.perform(get("/foo")
                        .header("Authorization", session.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}