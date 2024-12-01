package com.alura.api_rest.infra.web.dto;

import com.alura.api_rest.domain.enums.Specialty;
import com.alura.api_rest.infra.annotations.NormalizeStrings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NormalizeStrings
public class DoctorsDataListDTO {
  private Long id;
  private String name;
  private String email;
  private String crm;
  private Specialty specialty;
}
