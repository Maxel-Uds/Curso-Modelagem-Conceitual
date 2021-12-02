package com.maxel.cursomc.service.exceptions;

public class DataValidationException extends RuntimeException{

    public  DataValidationException(String msg) {
        super(msg);
    }

    public DataValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
