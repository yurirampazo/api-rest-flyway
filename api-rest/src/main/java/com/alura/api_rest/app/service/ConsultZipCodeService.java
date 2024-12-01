package com.alura.api_rest.app.service;

import com.alura.api_rest.infra.web.dto.AddressDTO;

public interface ConsultZipCodeService {
  AddressDTO validateAddressFromPatient(AddressDTO addressDTO);
}
