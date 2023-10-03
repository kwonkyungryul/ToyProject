package com.example.toyproject.modules.user.service;

import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.enums.RoleType;
import com.example.toyproject.modules.user.enums.UserStatus;
import com.example.toyproject.modules.user.repository.UserRepository;
import com.example.toyproject.modules.user.request.UserSaveRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(UserSaveRequest request) {
        return userRepository.save(
                new User(
                        null,
                        request.username(),
                        passwordEncoder.encode(request.password()),
                        RoleType.ACADEMY,
                        UserStatus.ACTIVE
                )
        );
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

}
