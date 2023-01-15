package com.github.vitorhenriquec.carrental.handler;

import com.github.vitorhenriquec.carrental.exception.AuthenticationException;
import com.github.vitorhenriquec.carrental.exception.CarNotFoundException;
import com.github.vitorhenriquec.carrental.exception.UserAlreadyExistsException;
import com.github.vitorhenriquec.carrental.exception.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {CarNotFoundException.class})
    public void handleCarNotFoundException() {
        log.error("method={};", "handleCarNotFoundException");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public void handleGenericException() {
        log.error("method={};", "handleGenericException");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {UserNotFoundException.class})
    public void handleUserNotFoundException() {
        log.error("method={};", "handleUserNotFoundException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public void handleUserAlreadyExistsException() {
        log.error("method={};", "handleUserAlreadyExistsException");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {AuthenticationException.class})
    public void handleAuthenticationException() {
        log.error("method={};", "handleAuthenticationException");
    }
}
