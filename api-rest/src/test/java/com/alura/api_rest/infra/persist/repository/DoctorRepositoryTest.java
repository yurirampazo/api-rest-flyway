package com.alura.api_rest.infra.persist.repository;

import com.alura.api_rest.domain.enums.Specialty;
import com.alura.api_rest.domain.model.Address;
import com.alura.api_rest.domain.model.Appointment;
import com.alura.api_rest.domain.model.DoctorsRegistrationDetails;
import com.alura.api_rest.domain.model.PatientRegistrationDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
/**
 * Anotation for using real db in automatized tests
 * */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

  @Autowired
  private DoctorRepository doctorRepository;
  @Autowired
  private TestEntityManager em;

  @Test
  @DisplayName("Should return null when doctor isnt available for appointment")
  void findDocBySpecialty() {
    var nextMonday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));

    var doctor = registerDoc("Doctor House", "dochouse@gmail.com", "369023", Specialty.ORTHOPEDICS, "12345678910");
    var patient = registerPatient("Patient test", "pat@gmail.com", "12345678901", "12345678910");
    registerAppointment(doctor, patient, nextMonday);

    var result = doctorRepository.findDocBySpecialty(Specialty.CARDIOLOGY, nextMonday);
    assertNull(result, "Must be null");
  }

  @Test
  @DisplayName("Should return doctor when he is available")
  void findDocBySpecialty2() {
    var nextMonday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));

    var doctor = registerDoc("Doctor House", "dochouse@gmail.com", "369023", Specialty.CARDIOLOGY, "12345678910");
    var patient = registerPatient("Patient test", "pat@gmail.com", "12345678901", "12345678910");
    registerAppointment(doctor, patient, nextMonday);

    var result = doctorRepository.findDocBySpecialty(Specialty.CARDIOLOGY, nextMonday);
    assertEquals(result, doctor, "Must be equals");
  }

  private void registerAppointment(DoctorsRegistrationDetails doctor, PatientRegistrationDetails patient, LocalDateTime date) {
    em.persist(new Appointment(null, doctor, patient, date));
  }

  private DoctorsRegistrationDetails registerDoc(String name, String email, String
        crm, Specialty specialty, String phone) {
    var medico = buildDoctor(name, email, crm, specialty, phone);
    em.persist(medico);
    return medico;
  }

  private PatientRegistrationDetails registerPatient(String nome, String email, String cpf, String phone) {
    var paciente = buildPatient(nome, email, cpf, phone);
    em.persist(paciente);
    return paciente;
  }

  private DoctorsRegistrationDetails buildDoctor(String name, String email, String crm, Specialty specialty, String phone) {
    return DoctorsRegistrationDetails.builder()
          .name(name)
          .crm(crm)
          .specialty(specialty)
          .email(email)
          .active(true)
          .phone(phone)
          .address(buildAddress())
          .build();
  }

  private PatientRegistrationDetails buildPatient(String name, String email, String cpf, String phone) {
    return PatientRegistrationDetails.builder()
          .name(name)
          .email(email)
          .phone(phone)
          .cpf(cpf)
          .active(true)
          .address(buildAddress())
          .build();
  }

  private Address buildAddress() {
    return new Address(
          "rua xpto",
          "bairro",
          "18132320",
          "Brasilia",
          "DF",
          null,
          "40"
    );
  }
}