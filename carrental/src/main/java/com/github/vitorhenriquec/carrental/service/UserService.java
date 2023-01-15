package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.exception.UserAlreadyExistsException;
import com.github.vitorhenriquec.carrental.request.UserSignUpRequest;

public interface UserService {
    void signUp(UserSignUpRequest request) throws UserAlreadyExistsException;
}
