package com.github.vitorhenriquec.carrental.security;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vitorhenriquec.carrental.exception.UserNotFoundException;
import com.github.vitorhenriquec.carrental.repository.UserRepository;
import com.github.vitorhenriquec.carrental.request.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthenticationFilter(UserRepository userRepository, ObjectMapper objectMapper, AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        super(authenticationManager);
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("method={};", "attemptAuthentication");

        try {
            final var loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

            final var userOptional = userRepository.findByEmail(loginRequest.getEmail());

            if(userOptional.isEmpty()) throw new UserNotFoundException("User not found");

            final var user = userOptional.get();

            final var authToken = new UsernamePasswordAuthenticationToken(user.getEmail(), loginRequest.getPassword());

            return authenticationManager.authenticate(authToken);
        } catch (StreamReadException e) {
            log.error("exception={};", e.getMessage());
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            log.error("exception={};", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("exception={};", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        log.info("method={};","successfulAuthentication");
        final var email = ( (UserCustomDetails) (authResult.getPrincipal())).getUsername();

        final var token = jwtUtil.generateToken(email);

        response.addHeader("Authorization","Bearer " + token);
    }
}
