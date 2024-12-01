package com.alura.api_rest.infra.web.dto;


import com.alura.api_rest.infra.annotations.NormalizeStrings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@NormalizeStrings
public class AppointmentResponseDTO {
  private Long id;
  private Long doctorId;
  private String dateTime;
}