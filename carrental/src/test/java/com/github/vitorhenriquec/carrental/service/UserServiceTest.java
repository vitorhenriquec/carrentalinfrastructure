package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.exception.UserAlreadyExistsException;
import com.github.vitorhenriquec.carrental.model.User;
import com.github.vitorhenriquec.carrental.repository.UserRepository;
import com.github.vitorhenriquec.carrental.request.UserSignUpRequest;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;


    @Test
    @DisplayName("Should save an user")
    public void shouldSaveUser() {
        final var userSignUpRequest = new UserSignUpRequest("email", "password");

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(new User(
                1L,
                "email",
                "password"
        ));

        Assertions.assertDoesNotThrow(
                ()  -> {
                    userService.signUp(userSignUpRequest);
                }
        );

        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should not save an already existing user")
    public void shouldNotSaveAnAlreadyExistingUser() {
        final var userSignUpRequest = new UserSignUpRequest("email", "password");

        when(userRepository.findByEmail("email")).thenReturn(Optional.of(new User(
                1L,
                "email",
                "password"
        )));

        Assertions.assertThrows(UserAlreadyExistsException.class,
                ()  -> {
                   userService.signUp(userSignUpRequest);
                }
        );

        verify(userRepository, times(1)).findByEmail(any());
        verify(userRepository, times(0)).save(any());
    }
}
