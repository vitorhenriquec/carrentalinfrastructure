package com.github.vitorhenriquec.carrental.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PasswordServiceImpl implements PasswordService{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String encode(String password) {
        log.info("method={}", "encode");
        return bCryptPasswordEncoder.encode(password.trim());
    }
}
