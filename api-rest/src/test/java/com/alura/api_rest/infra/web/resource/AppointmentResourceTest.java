package com.alura.api_rest.infra.web.resource;

import com.alura.api_rest.app.service.AppointmentService;
import com.alura.api_rest.domain.enums.Specialty;
import com.alura.api_rest.infra.web.dto.AppointmentDataRequestDTO;
import com.alura.api_rest.infra.web.dto.AppointmentResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentResourceTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JacksonTester<AppointmentDataRequestDTO> requestDTO;
  @Autowired
  private JacksonTester<AppointmentResponseDTO> responseDTO;
  @MockBean
  private AppointmentService appointmentService;


  @Test
  @DisplayName("Should return bad request 400 when given invalid information")
  void makeAppointment() throws Exception {
    var response = mockMvc.perform(post("/appointments"))
          .andReturn().getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }

  @Test
  @DisplayName("Should return 200 when registring appointment correctly")
  void makeAppointment2() throws Exception {
    var dateTime = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    var requestBody = AppointmentDataRequestDTO.builder()
          .doctorId(1L)
          .patientId(1L)
          .specialty(Specialty.CARDIOLOGY.name())
          .date(dateTime)
          .build();

    var responseDTObody = new AppointmentResponseDTO(1L, 1L, dateTime.toString());

    Mockito.when(appointmentService.saveAppointment(requestBody)).thenReturn(responseDTObody);

    var response = mockMvc.perform(post("/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestDTO.write(requestBody).getJson()))
          .andReturn().getResponse();

    var jsonResponse = responseDTO.write(responseDTObody)
          .getJson();

    assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    assertEquals(response.getContentAsString(), jsonResponse, "Must be equals");
  }
}