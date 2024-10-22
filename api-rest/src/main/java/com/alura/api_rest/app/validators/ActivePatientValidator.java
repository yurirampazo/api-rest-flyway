package com.alura.api_rest.app.validators;

import com.alura.api_rest.app.service.PatientService;
import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class ActivePatientValidator implements AppointmentValidator {

    private final PatientService patientService;

    public void validate(AppointmentDataRequestDTO data) {
        var isActivePatient = patientService.isActivePatient(data.getPatientId());
        if (!isActivePatient) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be active to get an appointment");
        }
    }
}
