package com.maxel.cursomc.resources.exceptions;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.maxel.cursomc.service.exceptions.AuthorizationException;
import com.maxel.cursomc.service.exceptions.DataIntegrityException;
import com.maxel.cursomc.service.exceptions.FileException;
import com.maxel.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    //@ExceptionHandler: serve para indicar que é um tratador de exceções de uma classe específica
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandartError> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest req) {
        StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Not Found",e.getMessage(), req.getRequestURI());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandartError> dataIntegrityException(DataIntegrityException e, HttpServletRequest req) {
        StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Data Integrity", e.getMessage(), req.getRequestURI());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandartError> validation(MethodArgumentNotValidException e, HttpServletRequest req) {
        ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de Validação", e.getMessage(), req.getRequestURI());

        for(FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandartError> authorizationException(AuthorizationException e, HttpServletRequest req) {
        StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso Negado", e.getMessage(), req.getRequestURI());
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandartError> fileException(FileException e, HttpServletRequest req) {
        StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro de Arquivo", e.getMessage(), req.getRequestURI());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandartError> amazonServiceException(AmazonServiceException e, HttpServletRequest req) {
        HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
        StandartError err = new StandartError(System.currentTimeMillis(), code.value(), "Erro Amazon Service", e.getMessage(), req.getRequestURI());
        return  ResponseEntity.status(code.value()).body(err);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandartError> amazonClientException(AmazonClientException e, HttpServletRequest req) {
        StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro Amazon Client", e.getMessage(), req.getRequestURI());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandartError> amazonS3Exception(AmazonS3Exception e, HttpServletRequest req) {
        StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro S3", e.getMessage(), req.getRequestURI());
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
