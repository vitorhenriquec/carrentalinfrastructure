package com.github.vitorhenriquec.carrental.security;

import com.github.vitorhenriquec.carrental.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Log4j2
public class JwtUtil {

    @Value("{jwt.expiration}")
    private Long expiration;

    @Value("{jwt.expiration}")
    private String secret;

    public String generateToken(String email) {
        log.info("method={}; email={};", "generateToken", email);
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public Boolean isValidToken(String token) throws AuthenticationException {
        log.info("method={}", "isValidToken");
        final var claims = getClaims(token);

        return !(claims.getSubject() == null || claims.getExpiration() == null || new Date().after(claims.getExpiration()));
    }

    public Claims getClaims(String token) throws AuthenticationException {
        log.info("method={}", "getClaims");
        try {
           return Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJwt(token).getBody();
        } catch (Exception ex) {
            throw new AuthenticationException();
        }
    }

    public String getSubject(String token) throws AuthenticationException {
        log.info("method={}", "getSubject");
        return getClaims(token).getSubject();
    }
}
