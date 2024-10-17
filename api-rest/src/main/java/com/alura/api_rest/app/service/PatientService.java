package com.alura.api_rest.app.service;

import com.alura.api_rest.domain.model.PatientRegistrationDetails;
import com.alura.api_rest.infra.persist.repository.PatientRepository;
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
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public PatientRegistrationDetailsDTO savePatient(PatientRegistrationDetailsDTO requestDto) {
        PatientRegistrationDetails doctor = patientRepository.save(PatientMapper.toModel(requestDto));
        return PatientMapper.toDto(doctor);
    }

    public Page<PatientDataListDTO> getPatients(Pageable pageable) {
        return patientRepository.findAllByActiveTrue(pageable).map(PatientMapper::toModelList);
    }

    private PatientRegistrationDetails getById(Long id) {
        return patientRepository.findPatientById(id);
    }

    public PatientRegistrationDetailsDTO getPatientById(Long id) {
        var dto = PatientMapper.toDto(getById(id));
        log.debug("Response from Database: {}", dto);
        return dto;
    }

    public void update(UpdateDoctorDTO requestDto) {
        var doctor = getById(requestDto.getId());
        if (StringUtils.isNotBlank(requestDto.getPhone())) {
            doctor.setPhone(requestDto.getPhone());
        }
        if (StringUtils.isNotBlank(requestDto.getName())) {
            doctor.setName(requestDto.getName());
        }
        var address = doctor.getAddress();
        if (!ObjectUtils.isEmpty(requestDto.getAddress())) {
            var addressDTO = requestDto.getAddress();
            if (StringUtils.isNotBlank(addressDTO.getCep())) {
                address.setCep(addressDTO.getCep());
            }
            if (StringUtils.isNotBlank(addressDTO.getState())) {
                address.setState(addressDTO.getState());
            }
            if (StringUtils.isNotBlank(addressDTO.getCity())) {
                address.setCity(addressDTO.getCity());
            }
            if (StringUtils.isNotBlank(addressDTO.getNeighborhood())) {
                address.setNeighborhood(addressDTO.getNeighborhood());
            }
            if (StringUtils.isNotBlank(addressDTO.getStreet())) {
                address.setStreet(addressDTO.getStreet());
            }
            if (StringUtils.isNotBlank(addressDTO.getNumber())) {
                address.setNumber(addressDTO.getNumber());
            }
            if (StringUtils.isNotBlank(addressDTO.getComplement())) {
                address.setComplement(addressDTO.getComplement());
            }
        }
        doctor.setAddress(address);
        patientRepository.save(doctor);
        log.info("Succefully updated registry");
    }

    public void delete(Long id) {
        var doctor = getById(id);
        if (ObjectUtils.isEmpty(doctor)) {
            log.info("No object found for deletion");
            return;
        }
        patientRepository.delete(doctor);
        log.info("Succefully deleted registry");
    }

    public void incativateById(Long id) {
        var doctor = getById(id);
        if (ObjectUtils.isEmpty(doctor)) {
            log.info("ID {} not found", id);
            return;
        }
        doctor.setActive(false);
        patientRepository.save(doctor);
        log.info("Succefully incativated {}", id);
        log.debug("Patient: {}", doctor);
    }

}
