package com.alura.api_rest.infra.web.mapper;

import com.alura.api_rest.domain.model.DoctorsRegistrationDetails;
import com.alura.api_rest.infra.web.dto.DoctorsDataListDTO;
import com.alura.api_rest.infra.web.dto.DoctorsRegistrationDetailsDTO;
import jakarta.persistence.EntityNotFoundException;

public final class DoctorMapper {

  public static DoctorsRegistrationDetailsDTO toDto(DoctorsRegistrationDetails request) {
    if (request == null) {
      throw new EntityNotFoundException("Entity of corresponding doctor id was not found");
    }
    return DoctorsRegistrationDetailsDTO.builder()
          .id(request.getId())
          .crm(request.getCrm())
          .name(request.getName())
          .email(request.getEmail())
          .phone(request.getPhone())
          .specialty(request.getSpecialty())
          .address(AddressMapper.toDto(request.getAddress()))
          .build();
  }

  public static DoctorsRegistrationDetails toModel(DoctorsRegistrationDetailsDTO dto) {
    return DoctorsRegistrationDetails.builder()
          .id(dto.getId())
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
    return DoctorsDataListDTO.builder()
          .id(dto.getId())
          .crm(dto.getCrm())
          .name(dto.getName())
          .email(dto.getEmail())
          .specialty(dto.getSpecialty())
          .build();
  }
}
