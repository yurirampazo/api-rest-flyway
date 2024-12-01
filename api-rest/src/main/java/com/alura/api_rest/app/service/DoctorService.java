package com.alura.api_rest.app.service;

import com.alura.api_rest.domain.enums.Specialty;
import com.alura.api_rest.domain.model.DoctorsRegistrationDetails;
import com.alura.api_rest.infra.persist.repository.DoctorRepository;
import com.alura.api_rest.infra.web.dto.DoctorsDataListDTO;
import com.alura.api_rest.infra.web.dto.DoctorsRegistrationDetailsDTO;
import com.alura.api_rest.infra.web.dto.UpdateDoctorDTO;
import com.alura.api_rest.infra.web.mapper.DoctorMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

public interface DoctorService {
  DoctorsRegistrationDetailsDTO saveDoctor(DoctorsRegistrationDetailsDTO requestDto);

  Page<DoctorsDataListDTO> getDoctors(Pageable pageable);

  DoctorsRegistrationDetailsDTO getDoctorById(Long id);

  void update(UpdateDoctorDTO requestDto);

  void delete(Long id);

  void inactivateById(Long id);

  DoctorsRegistrationDetails defineDoctorFromSpecialty(Specialty specialty, LocalDateTime date);

  Boolean findByActive(Long id);
}
