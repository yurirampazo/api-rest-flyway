package com.alura.api_rest.infra.exception;

import com.alura.api_rest.infra.exception.dto.DefaultErrorDTO;
import com.alura.api_rest.infra.exception.dto.UsernameAlreadyRegisteredException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class ExceptionsHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<DefaultErrorDTO> handleNotFoundError() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(buildErrror("Registry not found"));
  }

  private DefaultErrorDTO buildErrror(String msg) {
    String errMsg = Optional.ofNullable(msg).orElse("Unkown Error");

    return DefaultErrorDTO.builder()
          .message(errMsg)
          .build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<DefaultErrorDTO>> handleBadRequest(MethodArgumentNotValidException ex) {
    var errors = ex.getFieldErrors();
    return ResponseEntity.badRequest()
          .body(errors.stream().map(DefaultErrorDTO::new).toList());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<DefaultErrorDTO> handle400(HttpMessageNotReadableException ex) {
    return ResponseEntity.badRequest().body(buildErrror(ex.getMessage()));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<DefaultErrorDTO> handleBadCredentials() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(buildErrror("Invalid Credentials"));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<DefaultErrorDTO> handleAccessDenied() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(buildErrror("Denied Access"));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<DefaultErrorDTO> handleAuthentication() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(buildErrror("Authentication failed"));
  }

  @ExceptionHandler(UsernameAlreadyRegisteredException.class)
  public ResponseEntity<DefaultErrorDTO> handleServletException(UsernameAlreadyRegisteredException
                                                                      e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(buildErrror("Authentication failed"));
  }
}
