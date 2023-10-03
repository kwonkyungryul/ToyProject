package com.example.toyproject.modules.user.repository;

import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndStatusNot(String username, UserStatus status);
}
