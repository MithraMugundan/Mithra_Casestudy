package com.hexaware.casestudy.carrs.restcontroller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.hexaware.casestudy.carrs.dto.ReservationDTO;
import com.hexaware.casestudy.carrs.service.IReservationService;

@WebMvcTest(ReservationRestController.class)
class ReservationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReservationService reservationService;

    @Test
    void testGetReservationById() throws Exception {
        ReservationDTO dto = new ReservationDTO(
            1L, LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 12), 101L, 202L, "Confirmed"
        );

        when(reservationService.getReservationById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/reservations/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.reservationId", is(1)))
            .andExpect(jsonPath("$.reservationStatus", is("Confirmed")))
            .andExpect(jsonPath("$.customerId", is(101)))
            .andExpect(jsonPath("$.carId", is(202)));
    }

    @Test
    void testGetReservationsByCustomerId() throws Exception {
        List<ReservationDTO> reservations = Arrays.asList(
            new ReservationDTO(1L, LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 12), 101L, 202L, "Confirmed"),
            new ReservationDTO(2L, LocalDate.of(2025, 6, 15), LocalDate.of(2025, 6, 18), 101L, 204L, "Pending")
        );

        when(reservationService.getReservationsByCustomerId(101L)).thenReturn(reservations);

        mockMvc.perform(get("/api/reservations/customer/101"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(2)))
            .andExpect(jsonPath("$[0].reservationStatus", is("Confirmed")))
            .andExpect(jsonPath("$[1].reservationStatus", is("Pending")));
    }

    @Test
    void testCreateReservation() throws Exception {
        // Placeholder test — real one requires @RequestBody with JSON content
        // Here we just mock the service call and return an expected DTO
        ReservationDTO input = new ReservationDTO(null, LocalDate.of(2025, 6, 20), LocalDate.of(2025, 6, 22), 105L, 210L, "Pending");
        ReservationDTO saved = new ReservationDTO(3L, input.getStartDate(), input.getEndDate(), input.getCustomerId(), input.getCarId(), "Pending");

        when(reservationService.createReservation(any(ReservationDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "startDate": "2025-06-20",
                    "endDate": "2025-06-22",
                    "customerId": 105,
                    "carId": 210,
                    "reservationStatus": "Pending"
                }
            """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.reservationId", is(3)))
            .andExpect(jsonPath("$.customerId", is(105)))
            .andExpect(jsonPath("$.carId", is(210)))
            .andExpect(jsonPath("$.reservationStatus", is("Pending")));
    }

   
}
