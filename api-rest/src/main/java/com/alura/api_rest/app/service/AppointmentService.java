package com.alura.api_rest.app.service;

import com.alura.api_rest.domain.model.Appointment;
import com.alura.api_rest.domain.model.PatientRegistrationDetails;
import com.alura.api_rest.infra.persist.repository.AppointmentRepository;
import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;
import com.alura.api_rest.infra.web.dto.PatientDataListDTO;
import com.alura.api_rest.infra.web.dto.PatientRegistrationDetailsDTO;
import com.alura.api_rest.infra.web.dto.UpdateDoctorDTO;
import com.alura.api_rest.infra.web.mapper.PatientMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Log4j2
@Service
@RequiredArgsConstructor
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    public void saveAppointment(AppointmentDataRequestDTO requestDto) {
        appointmentRepository.save(new Appointment(null, null, null, requestDto.getDate()));
    }
}
