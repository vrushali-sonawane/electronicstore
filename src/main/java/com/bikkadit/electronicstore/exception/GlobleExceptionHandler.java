package com.bikkadit.electronicstore.exception;

import com.bikkadit.electronicstore.help.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler {


    //handler resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AppConstant> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){


        
        return  null;

    }
}
