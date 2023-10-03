package com.example.toyproject.config.security;

import com.example.toyproject.modules.common.exception.Exception401;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.enums.UserStatus;
import com.example.toyproject.modules.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService  {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userPS = userRepository.findByUsernameAndStatusNot(username, UserStatus.DELETE).orElseThrow(
                () -> new Exception401("아이디를 찾을 수 없습니다.")
        );
        if (userPS.getStatus().equals(UserStatus.DELETE)) {
            throw new Exception401("삭제된 계정입니다.");
        }
        return new CustomUserDetails(userPS);
    }
}
