package com.alura.api_rest.domain.model;

import com.alura.api_rest.infra.annotations.NormalizeStrings;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Setter
@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@NormalizeStrings
public class Address {
  private String street;
  private String neighborhood;
  private String cep;
  private String city;
  private String state;
  private String complement;
  private String number;
}
