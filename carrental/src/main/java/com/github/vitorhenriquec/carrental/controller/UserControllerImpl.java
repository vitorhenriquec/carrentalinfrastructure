package com.github.vitorhenriquec.carrental.controller;

import com.github.vitorhenriquec.carrental.exception.UserAlreadyExistsException;
import com.github.vitorhenriquec.carrental.request.UserSignUpRequest;
import com.github.vitorhenriquec.carrental.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Log4j2
public class UserControllerImpl implements UserController {

    private final UserServiceImpl userService;

    @Override
    @PostMapping(consumes= MediaType.APPLICATION_JSON_VALUE)
    public void signUp(
            @RequestBody UserSignUpRequest userSignUpRequest
    ) throws UserAlreadyExistsException {
        log.info("method={};", "signUp");
        userService.signUp(userSignUpRequest);
    }
}
