package com.alura.api_rest.infra.web.mapper;

import com.alura.api_rest.domain.model.DoctorsRegistrationDetails;
import com.alura.api_rest.infra.web.dto.DoctorsDataListDTO;
import com.alura.api_rest.infra.web.dto.DoctorsRegistrationDetailsDTO;
import com.alura.api_rest.infra.web.dto.UpdateDoctorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class DoctorMapper {

  public static DoctorsRegistrationDetailsDTO toDto(DoctorsRegistrationDetails request) {
    if (request == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request must not be null!");
    }
    return DoctorsRegistrationDetailsDTO.builder()
          .crm(request.getCrm())
          .name(request.getName())
          .email(request.getEmail())
          .phone(request.getPhone())
          .specialty(request.getSpecialty())
          .address(AddressMapper.toDto(request.getAddress()))
          .build();
  }

  public static DoctorsRegistrationDetails toModel(DoctorsRegistrationDetailsDTO dto) {
    if (dto == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request must not be null!");
    }
    return DoctorsRegistrationDetails.builder()
          .crm(dto.getCrm())
          .name(dto.getName())
          .email(dto.getEmail())
          .specialty(dto.getSpecialty())
          .phone(dto.getPhone())
          .active(dto.getActive())
          .address(AddressMapper.toModel(dto.getAddress()))
          .build();
  }

  public static DoctorsDataListDTO toModelList(DoctorsRegistrationDetails dto) {
    if (dto == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request must not be null!");
    }
    return DoctorsDataListDTO.builder()
          .id(dto.getId())
          .crm(dto.getCrm())
          .name(dto.getName())
          .email(dto.getEmail())
          .specialty(dto.getSpecialty())
          .build();
  }
}
