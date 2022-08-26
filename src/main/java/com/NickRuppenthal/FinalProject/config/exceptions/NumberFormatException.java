package com.NickRuppenthal.FinalProject.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NumberFormatException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public NumberFormatException(String exception){
        super(exception);
    }
}
