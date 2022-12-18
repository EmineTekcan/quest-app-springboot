package com.demos.questapp.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionResponse> illegalException(Exception exception, WebRequest webRequest){
        ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDateTime.now(),exception.getMessage(),"404");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handlerUserNotFoundException(ItemNotFoundException exception,WebRequest webRequest){
        ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDateTime.now(), exception.getMessage(), "404");
        return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemAlreadyExistException.class)
    public final ResponseEntity<ExceptionResponse> handlerUserAlreadyExistException(ItemAlreadyExistException exception){
        ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDateTime.now(),exception.getMessage(),"409");
        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
    }

}
