package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.repository.UserRepository;
import com.github.vitorhenriquec.carrental.security.UserCustomDetails;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class UserDetailsCustomService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("method={}", "loadUserByUsername");
        final var userOptional = userRepository.findByEmail(username);

        if(userOptional.isEmpty()) throw new UsernameNotFoundException("User not found");

        return new UserCustomDetails(userOptional.get());
    }
}
