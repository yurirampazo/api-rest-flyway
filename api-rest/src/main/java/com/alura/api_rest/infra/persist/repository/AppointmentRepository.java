package com.alura.api_rest.infra.persist.repository;

import com.alura.api_rest.domain.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
