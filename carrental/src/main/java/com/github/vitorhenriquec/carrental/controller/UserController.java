package com.github.vitorhenriquec.carrental.controller;

import com.github.vitorhenriquec.carrental.exception.UserAlreadyExistsException;
import com.github.vitorhenriquec.carrental.request.UserSignUpRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name= "User",
        description= "User service"
)
public interface UserController {

    void signUp(
            @Parameter(name="body", required = true, description = "User data to be saved")UserSignUpRequest userSignUpRequest
    ) throws UserAlreadyExistsException;
}
