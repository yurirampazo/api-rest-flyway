package com.alura.api_rest.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Setter
@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
  private String street;
  private String neighborhood;
  private String cep;
  private String city;
  private String state;
  private String complement;
  private String number;
}
