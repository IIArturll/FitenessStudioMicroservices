package com.example.mailservice.controllers;

import com.example.mailservice.core.exceptions.ErrorField;
import com.example.mailservice.core.exceptions.MultipleErrorResponse;
import com.example.mailservice.core.exceptions.SingleErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MultipleErrorResponse> fieldValidation(MethodArgumentNotValidException e) {
        MultipleErrorResponse errorResponse = new MultipleErrorResponse("error");
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

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<SingleErrorResponse> allErrorsHandler(Throwable e) {
        return ResponseEntity.status(500).body(new SingleErrorResponse("server error",
                e.getLocalizedMessage()));
    }
}
