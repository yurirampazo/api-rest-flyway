package com.alura.api_rest.infra.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AppointmentResponseDTO {
  private Long id;
  private Long doctorId;
  private String dateTime;
}