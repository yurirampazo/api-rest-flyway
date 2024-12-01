package com.alura.api_rest.domain.model;

import com.alura.api_rest.infra.annotations.NormalizeStrings;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "patients")
@Entity(name = "Patient")
@NormalizeStrings
public class PatientRegistrationDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String phone;
  private String cpf;
  @Embedded
  private Address address;
  private Boolean active = true;
}
