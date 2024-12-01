package com.alura.api_rest.infra.web.resource;

import com.alura.api_rest.app.service.impl.DoctorServiceImpl;
import com.alura.api_rest.infra.web.dto.DoctorsDataListDTO;
import com.alura.api_rest.infra.web.dto.DoctorsRegistrationDetailsDTO;
import com.alura.api_rest.infra.web.dto.UpdateDoctorDTO;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorResource {

  private final DoctorServiceImpl doctorServiceImpl;

  @PostMapping
  @Transactional
  public ResponseEntity<DoctorsRegistrationDetailsDTO> signUp(@RequestBody @Valid DoctorsRegistrationDetailsDTO requestDto,
                                                              UriComponentsBuilder uriBuilder) {
    log.debug("Request DTO: {}", new Gson().toJson(requestDto));
    DoctorsRegistrationDetailsDTO doctor = doctorServiceImpl.saveDoctor(requestDto);
    URI uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();
    return ResponseEntity.created(uri).body(doctor);
  }

  @GetMapping
  public ResponseEntity<Page<DoctorsDataListDTO>> getListDoctors(
        @PageableDefault(size = 3, sort = {"name"}, direction = Sort.Direction.ASC)
        Pageable pageable) {
    return ResponseEntity.ok().body(doctorServiceImpl.getDoctors(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DoctorsRegistrationDetailsDTO> getDoctorById(@PathVariable String id) {
    return ResponseEntity.ok().body(doctorServiceImpl.getDoctorById(Long.valueOf(id)));
  }

  @PutMapping
  @Transactional
  public ResponseEntity<Void> update(@RequestBody @Valid UpdateDoctorDTO requestDto) {
    log.debug("Request DTO: {}", new Gson().toJson(requestDto));
    doctorServiceImpl.update(requestDto);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  @Secured(value = "ROLE_ADMIN")
  @Transactional
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    log.debug("Request id to delete: {}", id);
    doctorServiceImpl.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/incativate-registry")
  @Transactional
  public ResponseEntity<Void> inactivate(@PathVariable Long id) {
    log.debug("Request id to be inactive: {}", id);
    doctorServiceImpl.inactivateById(id);
    return ResponseEntity.noContent().build();
  }

}
