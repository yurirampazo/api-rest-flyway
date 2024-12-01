package com.alura.api_rest.infra.web.dto;

import com.alura.api_rest.infra.annotations.NormalizeStrings;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NormalizeStrings
public class UpdateDoctorDTO {
  @NotNull
  private Long id;
  private String name;
  private String phone;
  private AddressDTO address;
}
