package it.academy.userservice.controllers;

import it.academy.userservice.core.exceptions.ErrorField;
import it.academy.userservice.core.exceptions.MultipleErrorResponse;
import it.academy.userservice.core.exceptions.SingleErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MultipleErrorResponse> fieldValidation(MethodArgumentNotValidException e) {
        MultipleErrorResponse errorResponse = new MultipleErrorResponse("validation error");
        e.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> errorResponse.add(new ErrorField(fieldError.getDefaultMessage(),
                        fieldError.getField())));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = {SingleErrorResponse.class})
    public ResponseEntity<SingleErrorResponse> SingleExHandle(SingleErrorResponse e) {
        return ResponseEntity.status(400).body(e);
    }

    @ExceptionHandler(value = {SQLException.class})
    public ResponseEntity<SingleErrorResponse> repeat(SQLException e) {
        int indStart = e.getMessage().lastIndexOf("Подробности:");
        String message = e.getMessage().substring(indStart + 13);
        return ResponseEntity.status(400).body(new SingleErrorResponse("err", message));
    }
}
