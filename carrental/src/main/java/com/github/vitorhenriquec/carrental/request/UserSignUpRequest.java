package com.github.vitorhenriquec.carrental.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpRequest {

    private String email;

    private String password;
}
