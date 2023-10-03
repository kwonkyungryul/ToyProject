package com.example.toyproject.modules.user.service;

import com.example.toyproject.modules.academy.request.AcademySaveRequest;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.enums.RoleType;
import com.example.toyproject.modules.user.enums.UserStatus;
import com.example.toyproject.modules.user.repository.UserRepository;
import com.example.toyproject.modules.user.request.UserSaveRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(UserSaveRequest request) {
        return userRepository.save(
                new User(
                        null,
                        request.username(),
                        request.password(),
                        RoleType.ACADEMY,
                        UserStatus.ACTIVE
                )
        );
    }

    public Optional<User> getUser(Long id) {
        return null;
    }

}
