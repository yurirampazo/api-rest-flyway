package com.alura.api_rest.infra.web.dto;

import com.alura.api_rest.infra.annotations.NormalizeStrings;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@NormalizeStrings
public class AddressDTO {
  @NotBlank
  private String street;
  @NotBlank
  private String neighborhood;
  @Pattern(regexp = "\\d{8}", message = "Must have 8 digits")
  private String cep;
  @NotBlank
  private String city;
  @NotBlank
  private String state;
  private String complement;
  private String number;
}
