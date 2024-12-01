package com.alura.api_rest.infra.persist.repository;

import com.alura.api_rest.domain.model.PatientRegistrationDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<PatientRegistrationDetails, Long> {
  PatientRegistrationDetails findPatientById(Long id);

  Page<PatientRegistrationDetails> findAllByActiveTrue(Pageable pageable);

  @Query("""
        select p.active from Patient p where p.id = :patientId
        """)
  boolean findByIdAndActiveTrue(@Param("patientId") Long patientId);
}
