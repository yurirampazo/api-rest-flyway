package com.alura.api_rest.infra.web.mapper;

import com.alura.api_rest.domain.model.Address;
import com.alura.api_rest.infra.web.dto.AddressDTO;
import jakarta.persistence.EntityNotFoundException;

public final class AddressMapper {
  private AddressMapper() {
  }

  public static AddressDTO toDto(Address request) {
    if (request == null) {
      throw new EntityNotFoundException("Entity of corresponding address id was not found");
    }
    return AddressDTO.builder()
          .street(request.getStreet())
          .cep(request.getCep())
          .state(request.getState())
          .city(request.getCity())
          .neighborhood(request.getNeighborhood())
          .complement(request.getComplement())
          .build();
  }

  public static Address toModel(AddressDTO dto) {
    return Address.builder()
          .street(dto.getStreet())
          .cep(dto.getCep())
          .state(dto.getState())
          .city(dto.getCity())
          .neighborhood(dto.getNeighborhood())
          .complement(dto.getComplement())
          .build();
  }

}
