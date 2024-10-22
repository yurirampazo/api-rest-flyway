package com.alura.api_rest.app.service;

import com.alura.api_rest.domain.enums.Specialty;
import com.alura.api_rest.domain.model.Appointment;
import com.alura.api_rest.domain.model.DoctorsRegistrationDetails;
import com.alura.api_rest.infra.persist.repository.AppointmentRepository;
import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;
import com.alura.api_rest.infra.web.mapper.DoctorMapper;
import com.alura.api_rest.infra.web.mapper.PatientMapper;
import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Log4j2
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;


    public void saveAppointment(AppointmentDataRequestDTO requestDto) {
        log.info("Saving appoinyment...");

        if (!appointmentRepository.existsById(requestDto.getPatientId()) &&
                (requestDto.getDoctorId() != null &&
                        !appointmentRepository.existsById(requestDto.getDoctorId()))) {
            log.info("ID Patient: {}", requestDto.getPatientId());
            log.info("ID Doctor: {}", requestDto.getDoctorId());
            throw new EntityNotFoundException("Id does not exist in database!");
        }


        var patientDTO = patientService.getPatientById(requestDto.getPatientId());
        var doctorDTO = defineDoctor(requestDto);
        var appointment = Appointment.builder()
                .patient(PatientMapper.toModel(patientDTO))
                .doctor(doctorDTO)
                .date(requestDto.getDate())
                .build();
        log.info("Saving appoinyment... {}", new Gson().toJson(appointment));
        appointmentRepository.save(appointment);
    }

    private DoctorsRegistrationDetails defineDoctor(AppointmentDataRequestDTO requestDto) {
        if (!ObjectUtils.isEmpty(requestDto)) {
            return DoctorMapper.toModel(doctorService.getDoctorById(requestDto.getDoctorId()));
        }
        Specialty specialty = Specialty.valueOf(requestDto.getSpecialty());

        return doctorService.defineDoctorFromSpecialty(specialty, requestDto.getDate());
    }
}
