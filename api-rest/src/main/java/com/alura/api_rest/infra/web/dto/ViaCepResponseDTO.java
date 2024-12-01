package com.alura.api_rest.infra.web.dto;

import com.alura.api_rest.infra.annotations.NormalizeStrings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NormalizeStrings
public class ViaCepResponseDTO {
  private String cep;
  private String logradouro;
  private String complemento;
  private String unidade;
  private String bairro;
  private String localidade;
  private String uf;
  private String estado;
  private String regiao;
  private String ibge;
  private String gia;
  private String ddd;
  private String siafi;
}
