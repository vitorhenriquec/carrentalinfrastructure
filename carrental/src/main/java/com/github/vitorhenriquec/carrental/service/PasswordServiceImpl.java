package com.github.vitorhenriquec.carrental.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class PasswordServiceImpl implements PasswordService{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String encode(String password) {
        log.info("method={}", "encode");
        return bCryptPasswordEncoder.encode(password.trim());
    }
}
