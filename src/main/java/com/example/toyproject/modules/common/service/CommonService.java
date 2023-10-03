package com.example.toyproject.modules.common.service;

import com.example.toyproject.config.security.SecurityTokenProvider;
import com.example.toyproject.modules.common.request.LoginRequest;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.enums.UserStatus;
import com.example.toyproject.modules.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommonService {

    private final UserRepository userRepository;
    private final SecurityTokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public CommonService(UserRepository userRepository, SecurityTokenProvider tokenProvider, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findUser(LoginRequest request) {
        return userRepository.findByUsernameAndStatusNot(request.username(), UserStatus.DELETE);
    }

    public String getToken(User user) {
        return tokenProvider.createToken(user);
    }

    public Boolean passwordCheck(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
