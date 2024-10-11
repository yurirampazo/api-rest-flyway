package com.alura.api_rest.infra.persist.repository;

import com.alura.api_rest.domain.model.DoctorsRegistrationDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<DoctorsRegistrationDetails, String> {
  DoctorsRegistrationDetails findById(Long id);

  Page<DoctorsRegistrationDetails> findAllByActiveTrue(Pageable pageable);
}
