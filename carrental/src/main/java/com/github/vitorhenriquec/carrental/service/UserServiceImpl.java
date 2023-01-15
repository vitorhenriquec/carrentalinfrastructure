package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.exception.UserAlreadyExistsException;
import com.github.vitorhenriquec.carrental.model.User;
import com.github.vitorhenriquec.carrental.repository.UserRepository;
import com.github.vitorhenriquec.carrental.request.UserSignUpRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordServiceImpl passwordService;

    public void signUp(UserSignUpRequest request) throws UserAlreadyExistsException {
        log.info("method={};", "signUp");

        if(!userRepository.findByEmail(request.getEmail()).isEmpty()) throw new UserAlreadyExistsException();

        var user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(passwordService.encode(request.getPassword()));

        userRepository.save(user);
    }
}
