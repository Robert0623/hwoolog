package com.hwoolog.api.service;

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

    @Transactional
    public Long signin(Login request) {
        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        return user.getId();
    }

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());

        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        User user = User.builder()
                .name(signup.getName())
                .email(signup.getEmail())
                .password(signup.getPassword())
                .build();

        userRepository.save(user);
    }
}
