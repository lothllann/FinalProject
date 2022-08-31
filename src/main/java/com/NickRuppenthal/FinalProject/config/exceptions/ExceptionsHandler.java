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

        ExceptionResponse eResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(eResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(Exception ex, WebRequest req){

        ExceptionResponse eResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(eResponse, HttpStatus.BAD_REQUEST);
    }


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
//        Map <String, String> errors = new HashMap<>();
//        String status_code = "Status code";
//        String code = "400";
//        errors.put(status_code,code);
//
//        ex.getBindingResult().getAllErrors().forEach((error)->{
//            String campo = ((FieldError) error).getField();
//            String erro = error.getDefaultMessage();
//            errors.put(campo, erro);
//        });
//
//        return errors;
//    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException exception, WebRequest req){

        ExceptionResponse eResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(eResponse,HttpStatus.NOT_FOUND);
    }








}
