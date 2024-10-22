package com.alura.api_rest.app.service;

import com.alura.api_rest.domain.model.Appointment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;

public class AppointmentValidations {


    public void validate (Appointment appointment) {
        var appointmentDate = appointment.getDate();
        var sunday = DayOfWeek.SUNDAY.equals(appointmentDate.getDayOfWeek());
        var beforeOpenClinic = appointmentDate.getHour() < 7;
        var afterClosingClinic = appointmentDate.getHour() > 18;

        if (sunday || beforeOpenClinic || afterClosingClinic) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"Appointment out of valid hour or day");
        }


    }

}
