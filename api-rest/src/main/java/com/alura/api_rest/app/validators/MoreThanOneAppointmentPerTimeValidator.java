package com.alura.api_rest.app.validators;

import com.alura.api_rest.app.service.DoctorService;
import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class MoreThanOneAppointmentPerTimeValidator implements AppointmentValidator {

    private final DoctorService doctorService;

    public void validate(AppointmentDataRequestDTO data) {
        var existsByMedicoId = doctorService.findByActive(data.getDoctorId());
        if (!existsByMedicoId) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error, Doctor already has an appointment at this time");
        }
    }
}
