package com.NickRuppenthal.FinalProject.config.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest req){

        ExceptionResponse eResponse = new ExceptionResponse(500, ex.getMessage());
        return new ResponseEntity<>(eResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequest(MethodArgumentNotValidException exception, WebRequest req){

        ExceptionResponse eResponse = new ExceptionResponse(400, exception.getMessage());
        return new ResponseEntity<>(eResponse,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException exception, WebRequest req){

        ExceptionResponse eResponse = new ExceptionResponse(404, exception.getMessage());
        return new ResponseEntity<>(eResponse,HttpStatus.NOT_FOUND);
    }






}
