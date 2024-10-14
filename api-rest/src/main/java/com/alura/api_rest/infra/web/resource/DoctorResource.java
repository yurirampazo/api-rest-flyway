package com.alura.api_rest.infra.web.resource;

import com.alura.api_rest.app.service.DoctorService;
import com.alura.api_rest.infra.web.dto.DoctorsDataListDTO;
import com.alura.api_rest.infra.web.dto.DoctorsRegistrationDetailsDTO;
import com.alura.api_rest.infra.web.dto.UpdateDoctorDTO;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorResource {

  private final DoctorService doctorService;

  @PostMapping
  @Transactional
  public ResponseEntity<DoctorsRegistrationDetailsDTO> signUp(@RequestBody @Valid DoctorsRegistrationDetailsDTO requestDto,
                                     UriComponentsBuilder uriBuilder) {
    log.debug("Request DTO: {}", new Gson().toJson(requestDto));
    DoctorsRegistrationDetailsDTO doctor = doctorService.saveDoctor(requestDto);
    URI uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();
    return ResponseEntity.created(uri).body(doctor);
  }

  @GetMapping
  public Page<DoctorsDataListDTO> getListDoctors(
        @PageableDefault(size = 3, sort = {"name"}, direction = Sort.Direction.ASC)
        Pageable pageable) {
    return doctorService.getDoctors(pageable);
  }

  @GetMapping("/{id}")
  public DoctorsRegistrationDetailsDTO getDoctorById(@PathVariable String id) {
    return doctorService.getDoctorById(Long.valueOf(id));
  }

  @PutMapping
  @Transactional
  public ResponseEntity<Void> update(@RequestBody @Valid UpdateDoctorDTO requestDto) {
    log.debug("Request DTO: {}", new Gson().toJson(requestDto));
    doctorService.update(requestDto);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    log.debug("Request id to delete: {}", id);
    doctorService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/incativate-registry")
  @Transactional
  public ResponseEntity<Void> inactivate(@PathVariable Long id) {
    log.debug("Request id to be inactive: {}", id);
    doctorService.incativateById(id);
    return ResponseEntity.noContent().build();
  }

}
