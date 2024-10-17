package com.alura.api_rest.infra.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRegistrationDetailsDTO {
  private Long id;
  @NotBlank
  private String name;
  @NotBlank
  @Email
  private String email;
  @NotBlank
  private String phone;
  @NotNull
  private Boolean active;
  @NotBlank
  @Pattern(regexp = "\\d{11}")
  private String cpf;
  @NotNull
  @Valid
  private AddressDTO address;
} 
