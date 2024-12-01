package com.alura.api_rest.domain.model;

import com.alura.api_rest.domain.enums.Specialty;
import com.alura.api_rest.infra.annotations.NormalizeStrings;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "doctors")
@Entity(name = "Doctor")
@NormalizeStrings
public class DoctorsRegistrationDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String phone;
  private String crm;
  @Enumerated(EnumType.STRING)
  private Specialty specialty;
  @Embedded
  private Address address;
  private Boolean active = true;
}
