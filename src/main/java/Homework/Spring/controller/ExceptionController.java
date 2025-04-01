package Homework.Spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import Homework.Spring.dto.response.ExceptionResponse;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    private ExceptionResponse error(RuntimeException ex) {
        return new ExceptionResponse(ex.getMessage());
    }
}
