package com.alura.api_rest.app.service;

import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;
import com.alura.api_rest.infra.web.dto.AppointmentResponseDTO;

public interface AppointmentService {
  AppointmentResponseDTO saveAppointment(AppointmentDataRequestDTO requestDto);
}
