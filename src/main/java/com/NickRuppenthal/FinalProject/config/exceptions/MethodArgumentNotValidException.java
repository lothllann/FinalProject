package com.NickRuppenthal.FinalProject.config.exceptions;

public class MethodArgumentNotValidException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MethodArgumentNotValidException(String e){
        super(e);
    }

}
