package com.alura.api_rest.app.validators;

import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AppointmentTimeBefore implements AppointmentValidator {

  public void validate(AppointmentDataRequestDTO appointment) {
    var appointmentDate = appointment.getDate();
    var now = LocalDateTime.now();
    var minutesDiff = Duration.between(now, appointmentDate).toMinutes();

    if (minutesDiff < 30) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Appoint must be set before 30 minutes of desired time");
    }
  }

}
