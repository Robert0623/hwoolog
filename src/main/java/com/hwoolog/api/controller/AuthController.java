package com.hwoolog.api.controller;

import com.hwoolog.api.config.AppConfig;
import com.hwoolog.api.request.Login;
import com.hwoolog.api.response.SessionResponse;
import com.hwoolog.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        Long userId = authService.signin(login);
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
//        String base64Key = Base64.getEncoder().encodeToString(KEY.getBytes(StandardCharsets.UTF_8));

        String jws = Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(new Date())
                .signWith(appConfig.getJwtSecreKey())
                .compact();

        return new SessionResponse(jws);
    }
}
