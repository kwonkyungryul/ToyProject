package com.example.toyproject.config.security;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.toyproject.modules.common.exception.Exception403;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class SecurityTokenFilter extends OncePerRequestFilter {

    private final SecurityTokenProvider securityTokenProvider;

    public SecurityTokenFilter(SecurityTokenProvider securityTokenProvider) {
        this.securityTokenProvider = securityTokenProvider;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String jwt = request.getHeader(securityTokenProvider.TOKEN_HEADER);
        if (StringUtils.hasText(jwt)) {

            if (!securityTokenProvider.validateToken(jwt)) {
                throw new Exception403("접근이 거부되었습니다.");
            }

            try {
                Authentication authentication = securityTokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (TokenExpiredException |SignatureVerificationException exception) {
                exception.printStackTrace();
            }
        }

        filterChain.doFilter(request, response);
    }
}
