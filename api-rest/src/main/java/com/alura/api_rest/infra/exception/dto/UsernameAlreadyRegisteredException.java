package com.alura.api_rest.infra.exception.dto;

public class UsernameAlreadyRegisteredException extends RuntimeException {
  public UsernameAlreadyRegisteredException(String s) {
    super(s);
  }
}
