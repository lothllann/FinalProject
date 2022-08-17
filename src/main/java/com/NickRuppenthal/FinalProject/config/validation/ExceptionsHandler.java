package com.NickRuppenthal.FinalProject.config.validation;

import com.NickRuppenthal.FinalProject.modelo.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionsHandler {

    @Autowired
    private MessageSource messageSource;



    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handle(MethodArgumentNotValidException exception){

        FieldError fieldError = exception.getBindingResult().getFieldError();
        String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
        ExceptionResponse erro = new ExceptionResponse(400, mensagem);

        return erro;
    }


//    @ExceptionHandler(value = Exception.class)
//    public ExceptionResponse handle(HttpServletRequest req, Exception exception) throws Exception {
//
//        if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null){ throw exception;}
//        ExceptionResponse erro = new ExceptionResponse();
//
//
//        String status = "status";
//        erro.setStatus_code(req.g);
//        erro.setMessage(exception.getMessage());
//
//        return erro;
//    }




}
