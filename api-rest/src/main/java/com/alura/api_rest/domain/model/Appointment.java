package com.alura.api_rest.domain.model;

import com.alura.api_rest.infra.annotations.NormalizeStrings;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointments")
@Entity(name = "Appointment")
@NormalizeStrings
public class Appointment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "doctor_id")
  private DoctorsRegistrationDetails doctor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "patient_id")
  private PatientRegistrationDetails patient;

  private LocalDateTime dateTime;
}
