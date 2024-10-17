package com.alura.api_rest.infra.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientDTO {
  @NotNull
  private Long id;
  private String name;
  private String phone;
  private AddressDTO address;
}
