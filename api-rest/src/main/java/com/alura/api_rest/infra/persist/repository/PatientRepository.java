package com.alura.api_rest.infra.persist.repository;

import com.alura.api_rest.domain.model.PatientRegistrationDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientRegistrationDetails, Long> {
  PatientRegistrationDetails findPatientById(Long id);

  Page<PatientRegistrationDetails> findAllByActiveTrue(Pageable pageable);
}
