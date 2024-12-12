package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class CustomeException extends RuntimeException{

    private static final Long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus httpStatus;

    public CustomeException(String m, HttpStatus status){
        this.message = m;
        this.httpStatus = status;
    }

    @Override
    public String getMessage(){
        return message;
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }

}
