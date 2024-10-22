package com.alura.api_rest.app.validators;

import com.alura.api_rest.app.service.DoctorService;
import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class ValidatorActiveDoctor implements AppointmentValidator {

    private final DoctorService doctorService;

    public void validate(AppointmentDataRequestDTO appointment) {
        if (appointment.getDoctorId() == null) {
            return;
        }

        var isActiveDoctor = doctorService.findByActive(appointment.getDoctorId());
        if (!isActiveDoctor) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Appointment with inactive doctor is not available");
        }
    }
}
