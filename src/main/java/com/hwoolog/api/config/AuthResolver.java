package com.hwoolog.api.config;

import com.hwoolog.api.exception.Unauthorized;
import com.hwoolog.api.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
//        if (servletRequest == null) {
//            log.error("servletRequest null");
//            throw new Unauthorized();
//        }
//
//        Cookie[] cookies = servletRequest.getCookies();
//        if (cookies == null || cookies.length == 0) {
//            log.error("쿠키가 없음");
//            throw new Unauthorized();
//        }
//
//        String accessToken = cookies[0].getValue();
//
//        Session session = sessionRepository.findByAccessToken(accessToken)
//                .orElseThrow(Unauthorized::new);
        String jws = webRequest.getHeader("Authorization");
        if (jws == null || "".equals(jws)) {
            throw new Unauthorized();
        }

        // byte[] decodedKey = Base64.getDecoder().decode(KEY);
        // SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256"); // java 표준 API
//        String base64Key = Base64.getEncoder().encodeToString(KEY.getBytes(StandardCharsets.UTF_8));
        try {

            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(appConfig.getJwtSecreKey())
                    .build()
                    .parseSignedClaims(jws);

            String userId = claims.getPayload().getSubject();
            return new UserSession(Long.parseLong(userId));
        } catch (JwtException e) {
            throw new Unauthorized();
        }
    }
}
