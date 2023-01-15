package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.model.User;
import com.github.vitorhenriquec.carrental.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserDetailsCustomServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserDetailsCustomService userDetailsCustomService;


    @Test
    @DisplayName("Should return an user")
    public void shouldReturnUser() {
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(new User(
                1L,
                "password",
                "email"
        )));

        final var user = Assertions.assertDoesNotThrow(
                ()  -> {
                    return userDetailsCustomService.loadUserByUsername("email");
                }
        );

        verify(userRepository, times(1)).findByEmail(any());
        Assertions.assertEquals(user.getUsername(), "email");
    }

    @Test
    @DisplayName("Should not return an user")
    public void shouldNotSaveAnAlreadyExistingUser() {

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                UsernameNotFoundException.class,
                ()  -> {
                    userDetailsCustomService.loadUserByUsername("email");
                }
        );

        verify(userRepository, times(1)).findByEmail(any());
    }
}
