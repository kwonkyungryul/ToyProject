package com.example.toyproject.config.security;

import com.example.toyproject.modules.common.exception.Exception401;
import com.example.toyproject.modules.common.exception.Exception403;
import com.example.toyproject.util.FilterResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
public class SecurityConfig {

    final SecurityTokenProvider securityTokenProvider;

    public SecurityConfig(SecurityTokenProvider securityTokenProvider) {
        this.securityTokenProvider = securityTokenProvider;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 해제
        http.csrf(AbstractHttpConfigurer::disable);

        // 2. iframe 거부
        http.headers(
                httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                        .frameOptions(HeadersConfigurer
                                .FrameOptionsConfig::disable
                        )
        );

        // 3. cors 재설정
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(
                configurationSource()
        ));

        // 4. jSessionId 사용 거부
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.formLogin(AbstractHttpConfigurer::disable);

        http.httpBasic(AbstractHttpConfigurer::disable);

        // 7. 커스텀 필터 적용
        http.apply(new SecurityFilterConfig(securityTokenProvider));

        // 8. 인증 실패 처리
        http.exceptionHandling(
                httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
                        (request, response, authException) -> {
                            log.warn("인증되지 않은 사용자가 자원에 접근하려 합니다 : "+authException.getMessage());
                            FilterResponseUtil.result(HttpStatus.UNAUTHORIZED, request, response, new Exception401("인증되지 않았습니다"));
                        }
                ).accessDeniedHandler(
                        (request, response, accessDeniedException) -> {
                            log.warn("권한이 없는 사용자가 자원에 접근하려 합니다 : "+accessDeniedException.getMessage());
                            FilterResponseUtil.result(HttpStatus.FORBIDDEN, request, response, new Exception403("권한이 없습니다"));
                        }
                )
        );

        // 10. 권한 실패 처리

        // 11. 인증, 권한 필터 설정
        http.authorizeHttpRequests(
                // TODO URL 설정 해야 함
                authorize -> authorize
//                        .requestMatchers("").authenticated()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
        );
        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
