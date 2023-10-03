package com.example.toyproject.modules.user.controller;

import com.example.toyproject.config.security.CustomUserDetails;
import com.example.toyproject.modules.common.exception.Exception400;
import com.example.toyproject.modules.common.exception.Exception403;
import com.example.toyproject.modules.user.UserModelAssembler;
import com.example.toyproject.modules.user.dto.UserModel;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.enums.UserConst;
import com.example.toyproject.modules.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long id
            )
    {

        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isEmpty()) {
            throw new Exception400(UserConst.notFound);
        }

        User user = optionalUser.get();

        if (!customUserDetails.getUser().getId().equals(user.getId())) {
            throw new Exception403("권한이 없습니다.");
        }

        return ResponseEntity.ok(
                new UserModelAssembler().toModel(user)
        );
    }
}
