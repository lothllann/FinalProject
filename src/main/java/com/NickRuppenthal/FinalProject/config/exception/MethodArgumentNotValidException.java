package com.NickRuppenthal.FinalProject.config.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MethodArgumentNotValidException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MethodArgumentNotValidException(String exception){
        super(exception);
    }


}
