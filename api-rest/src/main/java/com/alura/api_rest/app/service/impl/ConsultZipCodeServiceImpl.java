package com.alura.api_rest.app.service.impl;

import com.alura.api_rest.app.service.ConsultZipCodeService;
import com.alura.api_rest.infra.connectors.ViaCepConnector;
import com.alura.api_rest.infra.web.dto.AddressDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.alura.api_rest.app.utils.AppUtils.removeAccentsFromWords;

@Log4j2
@Service
@RequiredArgsConstructor
public class ConsultZipCodeServiceImpl implements ConsultZipCodeService {

  private final ViaCepConnector viaCepConnector;

  @Override
  public AddressDTO validateAddressFromPatient(AddressDTO addressDTO) {

    addressDTO.setCity(removeAccentsFromWords(addressDTO.getCity()));

    var zipCode = Optional.of(addressDTO)
          .map(address -> Optional.ofNullable(address.getCep()).orElseThrow(IllegalArgumentException::new))
          .orElseThrow(IllegalAccessError::new);
    var responseAddress = consultZipCodeAddress(zipCode);

    var areEquals = checkIfAddressesAreEquals(addressDTO, responseAddress);

    return areEquals ? responseAddress : addressDTO;
  }

  private Boolean checkIfAddressesAreEquals(AddressDTO addressDTO, AddressDTO responseAddress) {
    if (addressDTO.getCep().equalsIgnoreCase(responseAddress.getCep()) &&
          addressDTO.getCity().equalsIgnoreCase(responseAddress.getCity()) &&
          addressDTO.getState().equalsIgnoreCase(responseAddress.getState()) &&
          (addressDTO.getNeighborhood()).equalsIgnoreCase(responseAddress.getNeighborhood())
    ) {
      var street = addressDTO.getStreet();
      var streetResponse = responseAddress.getStreet();

      return  streetResponse.equalsIgnoreCase(street)
            && addressDTO.getNumber().equals(responseAddress.getNumber());
    }
    return false;
  }

  private AddressDTO consultZipCodeAddress(String zipCode) {
    try {
      var responseAddressDTO = viaCepConnector.findAddressByZipCode(zipCode);
      return AddressDTO.builder()
            .cep(responseAddressDTO.getCep().replaceAll("\\D", ""))
            .city(responseAddressDTO.getLocalidade())
            .state(responseAddressDTO.getUf())
            .street(responseAddressDTO.getLogradouro())
            .complement(responseAddressDTO.getComplemento())
            .neighborhood(responseAddressDTO.getBairro())
            .build();
    } catch (FeignException e) {
      log.error("Feign Exception Caught integrating... ", e);
      throw e;
    }
  }


}
