package com.hwoolog.api.service;

import com.hwoolog.api.crypto.PasswordEncoder;
import com.hwoolog.api.domain.User;
import com.hwoolog.api.exception.AlreadyExistsEmailException;
import com.hwoolog.api.exception.InvalidSigninInformation;
import com.hwoolog.api.repository.UserRepository;
import com.hwoolog.api.request.Login;
import com.hwoolog.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signin(Login request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidSigninInformation::new);

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!matches) {
            throw new InvalidSigninInformation();
        }

        return user.getId();
    }

    public void signup(Signup signup) {
        // email 중복 체크
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());

        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        // password에 SCrypt 적용
        String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());

        User user = User.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(encryptedPassword)
                .build();

        userRepository.save(user);
    }
}
