package com.alura.api_rest.app.validators;

import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;

public interface AppointmentValidator {
    void validate(AppointmentDataRequestDTO data);
}
