package com.example.toyproject.config.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityFilterConfig extends AbstractHttpConfigurer<SecurityFilterConfig, HttpSecurity> {

    private final SecurityTokenProvider securityTokenProvider;

    public SecurityFilterConfig(SecurityTokenProvider securityTokenProvider) {
        this.securityTokenProvider = securityTokenProvider;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        SecurityTokenFilter securityTokenFilter = new SecurityTokenFilter(securityTokenProvider);
        builder.addFilterBefore(securityTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
