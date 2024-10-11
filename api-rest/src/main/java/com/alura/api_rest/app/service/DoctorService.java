package com.alura.api_rest.app.service;

import com.alura.api_rest.domain.model.Address;
import com.alura.api_rest.domain.model.DoctorsRegistrationDetails;
import com.alura.api_rest.infra.persist.repository.DoctorRepository;
import com.alura.api_rest.infra.web.dto.AddressDTO;
import com.alura.api_rest.infra.web.dto.DoctorsDataListDTO;
import com.alura.api_rest.infra.web.dto.DoctorsRegistrationDetailsDTO;
import com.alura.api_rest.infra.web.dto.UpdateDoctorDTO;
import com.alura.api_rest.infra.web.mapper.DoctorMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class DoctorService {
  @Autowired
  private DoctorRepository doctorRepository;

  public void saveDoctor(DoctorsRegistrationDetailsDTO requestDto) {
    doctorRepository.save(DoctorMapper.toModel(requestDto));
  }

  public Page<DoctorsDataListDTO> getDoctors(Pageable pageable) {
    return doctorRepository.findAllByActiveTrue(pageable).map(DoctorMapper::toModelList);
  }

  public DoctorsRegistrationDetails getById(Long id) {
    return doctorRepository.findById(id);
  }

  public void update(UpdateDoctorDTO requestDto) {
    DoctorsRegistrationDetails doctor = getById(requestDto.getId());
    if (StringUtils.isNotBlank(requestDto.getPhone())) {
      doctor.setPhone(requestDto.getPhone());
    }
    if (StringUtils.isNotBlank(requestDto.getName())) {
      doctor.setName(requestDto.getName());
    }
    Address address = doctor.getAddress();
    if (!ObjectUtils.isEmpty(requestDto.getAddress())) {
      AddressDTO addressDTO = requestDto.getAddress() ;
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
    doctorRepository.save(doctor);
    log.info("Succefully updated doctor registry");
  }

  public void delete(Long id) {
    DoctorsRegistrationDetails doctor = getById(id);
    if (ObjectUtils.isEmpty(doctor)) {
      log.info("No object found for deletion");
      return;
    }
    doctorRepository.delete(doctor);
    log.info("Succefully deleted doctor registry");
  }

  public void incativateById(Long id) {
    DoctorsRegistrationDetails doctor = getById(id);
    if (ObjectUtils.isEmpty(doctor)) {
      log.info("Doctor ID {} not found", id);
      return;
    }
    doctor.setActive(false);
    doctorRepository.save(doctor);
    log.info("Succefully incativated doctor {}", id);
    log.debug("Docotr: {}", doctor);
  }

}