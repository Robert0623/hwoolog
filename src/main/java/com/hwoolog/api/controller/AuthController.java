package com.hwoolog.api.controller;

import com.hwoolog.api.request.Login;
import com.hwoolog.api.response.SessionResponse;
import com.hwoolog.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        String accessToken = authService.signin(login);
//        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
//                .domain("localhost") // TODO - 서버 환경에 따른 분리 필요
//                .path("/")
//                .httpOnly(true)
//                .secure(false)
//                .maxAge(Duration.ofDays(30))
//                .sameSite("Strict")
//                .build();
//        log.info(">>>>>>>>>> cookie={}", cookie.toString());
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, cookie.toString())
//                .build();
        SecretKey key = Jwts.SIG.HS256.key().build();
        String jws = Jwts.builder().subject("Joe").signWith(key).compact();

        return new SessionResponse(jws);
    }
}
