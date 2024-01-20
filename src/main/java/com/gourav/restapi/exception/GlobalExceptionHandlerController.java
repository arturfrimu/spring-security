package com.gourav.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(value = InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handleAllExceptions(Exception ex) {
        return ex.getMessage();
    }
}
