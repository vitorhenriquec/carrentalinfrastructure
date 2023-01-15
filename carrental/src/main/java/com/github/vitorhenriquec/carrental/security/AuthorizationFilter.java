package com.github.vitorhenriquec.carrental.security;

import com.github.vitorhenriquec.carrental.exception.AuthenticationException;
import com.github.vitorhenriquec.carrental.exception.UserNotFoundException;
import com.github.vitorhenriquec.carrental.service.UserDetailsCustomService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsCustomService userDetailsCustomService;

    private final JwtUtil jwtUtil;

    public AuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsCustomService userDetailsCustomService, JwtUtil jwtUtil) {
        super(authenticationManager);

        this.userDetailsCustomService = userDetailsCustomService;

        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("method={};", "doFilterInternal");
        final var authorization = request.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken auth;

            try {
                auth = getAuthenticationToken(authorization.split(" ")[1]);
            } catch (AuthenticationException e) {
                throw new RuntimeException(e);
            }

            SecurityContextHolder.getContext().setAuthentication(auth);

        }
        chain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(String token) throws AuthenticationException {
        log.info("method={};", "getAuthenticationToken");
        if(!jwtUtil.isValidToken(token)) throw new AuthenticationException();

        final var subject = jwtUtil.getSubject(token);

        final var user = userDetailsCustomService.loadUserByUsername(subject);

        if(!user.isEnabled()) throw new UserNotFoundException();

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
