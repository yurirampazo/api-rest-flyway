package com.alura.api_rest.infra.web.mapper;

import com.alura.api_rest.domain.model.Address;
import com.alura.api_rest.infra.web.dto.AddressDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class AddressMapper {
  private AddressMapper() {}
  public static AddressDTO toDto(Address request) {
    if (request == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request must not be null!");
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
    if (dto == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request must not be null!");
    }
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
