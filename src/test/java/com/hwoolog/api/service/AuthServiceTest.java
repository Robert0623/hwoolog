package com.hwoolog.api.service;

import com.hwoolog.api.domain.User;
import com.hwoolog.api.exception.AlreadyExistsEmailException;
import com.hwoolog.api.repository.UserRepository;
import com.hwoolog.api.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;


    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        Signup signup = Signup.builder()
                .name("hwoo")
                .email("aaa@aaa.com")
                .password("1234")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("hwoo", user.getName());
        assertEquals("aaa@aaa.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertNotEquals("1234", user.getPassword());
    }

    @Test
    @DisplayName("회원가입시 중복된 이메일 체크")
    void test2() {
        // given
        User user = User.builder()
                .name("hwoo")
                .email("aaa@aaa.com")
                .password("1234")
                .build();

        userRepository.save(user);

        Signup signup = Signup.builder()
                .name("hwoo")
                .email("aaa@aaa.com")
                .password("1234")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }
}