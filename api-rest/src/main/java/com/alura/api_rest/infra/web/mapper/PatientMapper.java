package com.alura.api_rest.infra.web.mapper;

import com.alura.api_rest.domain.model.PatientRegistrationDetails;
import com.alura.api_rest.infra.web.dto.PatientDataListDTO;
import com.alura.api_rest.infra.web.dto.PatientRegistrationDetailsDTO;
import jakarta.persistence.EntityNotFoundException;

public final class PatientMapper {

  public static PatientRegistrationDetailsDTO toDto(PatientRegistrationDetails request) {
    if (request == null) {
      throw new EntityNotFoundException("Entity of corresponding doctor id was not found");
    }
    return PatientRegistrationDetailsDTO.builder()
          .id(request.getId())
          .cpf(request.getCpf())
          .name(request.getName())
          .email(request.getEmail())
          .phone(request.getPhone())
          .address(AddressMapper.toDto(request.getAddress()))
          .build();
  }

  public static PatientRegistrationDetails toModel(PatientRegistrationDetailsDTO dto) {
    return PatientRegistrationDetails.builder()
          .id(dto.getId())
          .cpf(dto.getCpf())
          .name(dto.getName())
          .email(dto.getEmail())
          .phone(dto.getPhone())
          .active(dto.getActive())
          .address(AddressMapper.toModel(dto.getAddress()))
          .build();
  }

  public static PatientDataListDTO toModelList(PatientRegistrationDetails dto) {
    return PatientDataListDTO.builder()
          .id(dto.getId())
          .name(dto.getName())
          .email(dto.getEmail())
          .build();
  }
}
