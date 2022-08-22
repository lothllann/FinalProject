package com.NickRuppenthal.FinalProject.modelo;

import java.io.Serializable;

public class ExceptionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer status_code;
    private String message;

    public ExceptionResponse() {}

    public ExceptionResponse(Integer status_code, String message) {
        this.status_code = status_code;
        this.message = message;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


