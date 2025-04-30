package com.hwoolog.api.controller;

import com.hwoolog.api.domain.User;
import com.hwoolog.api.exception.InvalidSigninInformation;
import com.hwoolog.api.repository.UserRepository;
import com.hwoolog.api.request.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public User login(@RequestBody Login login) {
        log.info(">>>login={}", login);

        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        return user;
    }
}
