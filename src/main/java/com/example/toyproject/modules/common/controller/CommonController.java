package com.example.toyproject.modules.common.controller;

import com.example.toyproject.config.security.SecurityTokenProvider;
import com.example.toyproject.modules.common.exception.Exception400;
import com.example.toyproject.modules.common.request.LoginRequest;
import com.example.toyproject.modules.common.service.CommonService;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.enums.UserConst;
import com.example.toyproject.modules.user.enums.UserStatus;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CommonController {

    private final CommonService commonService;

    private final SecurityTokenProvider tokenProvider;

    public CommonController(CommonService commonService, SecurityTokenProvider tokenProvider) {
        this.commonService = commonService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new Exception400(errors.getAllErrors().get(0).getDefaultMessage());
        }

        Optional<User> optionalUser = commonService.findUser(request);

        if (optionalUser.isEmpty()) {
            throw new Exception400("ID 혹은 Password가 잘못되었습니다.");
        }

        User user = optionalUser.get();

        if (!commonService.passwordCheck(request.password(), user.getPassword())) {
            throw new Exception400("ID 혹은 Password가 잘못되었습니다.");
        }

//        if (!user.getPassword().equals(request.password())) {
//            throw new Exception400("ID 혹은 Password가 잘못되었습니다.");
//        }

        String jwt = commonService.getToken(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add(tokenProvider.TOKEN_HEADER, tokenProvider.TOKEN_PREFIX + jwt);
        headers.add("Content-Type", "application/json;charset=utf-8");
        Map<String, String> map = new HashMap<>();
        map.put("message", "로그인이 완료되었습니다.");

        return new ResponseEntity<>(map, headers, HttpStatus.OK);
    }
}
