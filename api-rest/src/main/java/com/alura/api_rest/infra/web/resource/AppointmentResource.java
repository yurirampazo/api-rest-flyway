package com.alura.api_rest.infra.web.resource;

import com.alura.api_rest.app.service.AppointmentService;
import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentResource {

  private final AppointmentService appointmentService;

  @PostMapping
  @Transactional
  public ResponseEntity<Void> makeAppointment(@RequestBody @Valid AppointmentDataRequestDTO requestDto,
                                     UriComponentsBuilder uriBuilder) {
    appointmentService.saveAppointment(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
