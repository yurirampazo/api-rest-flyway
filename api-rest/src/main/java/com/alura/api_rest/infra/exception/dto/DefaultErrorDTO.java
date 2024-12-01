package com.alura.api_rest.infra.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultErrorDTO {
  private String field;
  private String message;

  public DefaultErrorDTO(FieldError error) {
    this.field = error.getField();
    this.message = error.getDefaultMessage();
  }

}
