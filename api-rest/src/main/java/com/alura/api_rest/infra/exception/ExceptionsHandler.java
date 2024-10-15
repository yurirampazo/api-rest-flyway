package com.alura.api_rest.infra.exception;

import com.alura.api_rest.infra.exception.dto.DefaultErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleNotFoundError(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DefaultErrorDTO>> handleBadRequest(MethodArgumentNotValidException ex){
        List<FieldError> errors = ex.getFieldErrors();
        
        return ResponseEntity.badRequest()
                .body(errors.stream().map(DefaultErrorDTO::new).toList());
    }
}
