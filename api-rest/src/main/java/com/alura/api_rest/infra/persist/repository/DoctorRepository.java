package com.alura.api_rest.infra.persist.repository;

import com.alura.api_rest.domain.enums.Specialty;
import com.alura.api_rest.domain.model.DoctorsRegistrationDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<DoctorsRegistrationDetails, String> {
    DoctorsRegistrationDetails findById(Long id);

    Page<DoctorsRegistrationDetails> findAllByActiveTrue(Pageable pageable);

  @Query("""
          SELECT d FROM Doctor d
          WHERE d.active = true
          AND d.specialty = :specialty
          AND d.id not in(
            SELECT a.doctor.id FROM Appointment a WHERE
            a.date = :date
          )
          ORDER BY rand()
          limit 1
          """)
    DoctorsRegistrationDetails findDocBySpecialty(@Param("specialty") Specialty specialty, @Param("date") LocalDateTime date);
}
