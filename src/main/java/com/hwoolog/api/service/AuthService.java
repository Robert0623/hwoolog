package com.hwoolog.api.service;

import com.hwoolog.api.domain.User;
import com.hwoolog.api.exception.AlreadyExistsEmailException;
import com.hwoolog.api.repository.UserRepository;
import com.hwoolog.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(Signup signup) {
        // email 중복 체크
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());

        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        // password에 SCrypt 적용
        String encryptedPassword = passwordEncoder.encode(signup.getPassword());

        User user = User.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(encryptedPassword)
                .build();

        userRepository.save(user);
    }
}
