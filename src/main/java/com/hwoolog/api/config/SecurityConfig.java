package com.hwoolog.api.config;

import com.hwoolog.api.domain.User;
import com.hwoolog.api.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/favicon.ico")
                .requestMatchers("/error")
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/signup").permitAll()
                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin")
                        .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasAuthority('WRITE')"))
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/"))
                .rememberMe(rm -> rm.rememberMeParameter("remember")
                        .alwaysRemember(false)
                        .tokenValiditySeconds(60 * 60 * 24 * 30))
                .csrf(csrf -> csrf.disable())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));

            return new UserPrincipal(user);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder(16, 8, 1, 32, 16);
    }
}
