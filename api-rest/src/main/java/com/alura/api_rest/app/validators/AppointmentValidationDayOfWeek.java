package com.alura.api_rest.app.validators;

import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;

@Component
public class AppointmentValidationDayOfWeek implements AppointmentValidator {

    public void validate(AppointmentDataRequestDTO appointment) {
        var appointmentDate = appointment.getDate();
        var sunday = DayOfWeek.SUNDAY.equals(appointmentDate.getDayOfWeek());
        var beforeOpenClinic = appointmentDate.getHour() < 7;
        var afterClosingClinic = appointmentDate.getHour() > 18;

        if (sunday || beforeOpenClinic || afterClosingClinic) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment out of valid hour or day");
        }
    }
}
