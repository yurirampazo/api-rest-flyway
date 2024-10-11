package com.alura.api_rest.infra.web.dto;

import com.alura.api_rest.domain.enums.Specialty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorsRegistrationDetailsDTO {
  @NotBlank
  private String name;
  @NotBlank
  @Email
  private String email;
  @NotBlank
  private String phone;
  @NotBlank
  @Pattern(regexp = "\\d{4,6}")
  private String crm;
  @NotNull
  @Enumerated(EnumType.STRING)
  private Specialty specialty;
  @NotNull
  @Valid
  private AddressDTO address;
} 
