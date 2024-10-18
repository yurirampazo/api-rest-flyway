package com.alura.api_rest.infra.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDataRequestDTO {

    @NotBlank
    private Long doctorId;

    @NotBlank
    private Long patientId;

    @Future
    @Pattern(regexp = "yyyy-MM-dd HH:mm", message = "Must be in yyyy-MM-dd HH:mm pattern")
    private LocalDateTime date;

}
