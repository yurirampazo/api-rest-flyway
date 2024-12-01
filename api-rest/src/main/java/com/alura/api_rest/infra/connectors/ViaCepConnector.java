package com.alura.api_rest.infra.connectors;

import com.alura.api_rest.infra.web.dto.ViaCepResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "viaCepConecctor",
      url = "${integrations.host.via-cep}$" +
            "{integrations.context-path.via-cep}")
public interface ViaCepConnector {

  @GetMapping(value = "${integrations.enpoint.get-zip-code}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
  )
  ViaCepResponseDTO findAddressByZipCode(@PathVariable("zipCode") String postId);
}
