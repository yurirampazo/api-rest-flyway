package com.alura.api_rest.app.service.impl;

import com.alura.api_rest.app.service.AppointmentService;
import com.alura.api_rest.app.validators.AppointmentValidator;
import com.alura.api_rest.domain.enums.Specialty;
import com.alura.api_rest.domain.model.Appointment;
import com.alura.api_rest.domain.model.DoctorsRegistrationDetails;
import com.alura.api_rest.infra.persist.repository.AppointmentRepository;
import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;
import com.alura.api_rest.infra.web.dto.AppointmentResponseDTO;
import com.alura.api_rest.infra.web.mapper.DoctorMapper;
import com.alura.api_rest.infra.web.mapper.PatientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

  private final AppointmentRepository appointmentRepository;
  private final DoctorServiceImpl doctorServiceImpl;
  private final PatientServiceImpl patientServiceImpl;
  private final List<AppointmentValidator> appointmentValidators;


  public AppointmentResponseDTO saveAppointment(AppointmentDataRequestDTO requestDto) {
    log.info("Saving appoinyment...");
    Long docId = Optional.ofNullable(requestDto.getDoctorId()).orElse(-1L);
    Long patId = Optional.ofNullable(requestDto.getPatientId()).orElse(-1L);

    if (doctorServiceImpl.getDoctorById(docId) == null ||
          patientServiceImpl.getPatientById(patId) == null) {
      log.info("ID Patient: {}", patId);
      log.info("ID Doctor: {}", docId);
      throw new EntityNotFoundException("Id does not exist in database!");
    }

    var patientDTO = patientServiceImpl.getPatientById(requestDto.getPatientId());
    var doctorDTO = defineDoctor(requestDto);
    var appointment = Appointment.builder()
          .patient(PatientMapper.toModel(patientDTO))
          .doctor(doctorDTO)
          .dateTime(requestDto.getDate())
          .build();

    log.info("Starting validation filters for appointment!");
    appointmentValidators.forEach(validator -> validator.validate(requestDto));
    Appointment save = appointmentRepository.save(appointment);
    return AppointmentResponseDTO.builder()
          .id(save.getId())
          .doctorId(save.getDoctor().getId())
          .dateTime(save.getDateTime().toString())
          .build();
  }

  private DoctorsRegistrationDetails defineDoctor(AppointmentDataRequestDTO requestDto) {
    if (!ObjectUtils.isEmpty(requestDto)) {
      return DoctorMapper.toModel(doctorServiceImpl.getDoctorById(requestDto.getDoctorId()));
    }
    Specialty specialty = Specialty.valueOf(requestDto.getSpecialty());

    return doctorServiceImpl.defineDoctorFromSpecialty(specialty, requestDto.getDate());
  }
}