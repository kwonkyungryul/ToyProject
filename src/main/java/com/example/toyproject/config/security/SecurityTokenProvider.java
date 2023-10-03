package com.example.toyproject.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.toyproject.modules.user.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SecurityTokenProvider {

    private final CustomUserDetailsService userDetailsService;

    public final String TOKEN_HEADER = "Authorization";
    public final String TOKEN_PREFIX = "Bearer ";
    public final String SECRET_KEY = "attendance";
    private static final Integer EXP = 1000 * 60 * 60* 24;


    private final Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY);

    public SecurityTokenProvider(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String createToken(User user) {

        String token = null;

        try {
            token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                    .withClaim("id", user.getId())
                    .withClaim("role", user.getRole().name())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        return token;
    }

    public boolean validateToken(String token) {
        try {
            DecodedJWT verify = JWT.require(algorithm).build().verify(token);

        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT verify = JWT.require(algorithm).build().verify(token);
        String subject = verify.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
