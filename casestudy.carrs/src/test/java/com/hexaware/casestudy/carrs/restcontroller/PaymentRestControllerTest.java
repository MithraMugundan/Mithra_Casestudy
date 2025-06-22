package com.hexaware.casestudy.carrs.restcontroller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.hexaware.casestudy.carrs.dto.PaymentDTO;
import com.hexaware.casestudy.carrs.service.IPaymentService;

@WebMvcTest(PaymentRestController.class)
class PaymentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPaymentService paymentService;

    @Test
    void testGetPaymentById() throws Exception {
        PaymentDTO payment = new PaymentDTO(
        );

        when(paymentService.getPaymentById(1L)).thenReturn(payment);

        mockMvc.perform(get("/api/payments/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.paymentId", is(1)))
            .andExpect(jsonPath("$.paymentType", is("Credit Card")))
            .andExpect(jsonPath("$.amount", is(5000.0)))
            .andExpect(jsonPath("$.reservationId", is(1001)))
            .andExpect(jsonPath("$.paymentStatus", is("Success")));
    }

    @Test
    void testGetAllPayments() throws Exception {
        List<PaymentDTO> payments = Arrays.asList(
            new PaymentDTO(),
            new PaymentDTO()
        );

        when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/api/payments/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(2)))
            .andExpect(jsonPath("$[0].paymentType", is("UPI")))
            .andExpect(jsonPath("$[1].paymentStatus", is("Failed")));
    }

    @Test
    void testGetPaymentsByReservationId() throws Exception {
        List<PaymentDTO> payments = Arrays.asList(
            new PaymentDTO()
        );

        when(paymentService.getPaymentsByReservationId(1010L)).thenReturn(payments);

        mockMvc.perform(get("/api/payments/reservation/1010"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].reservationId", is(1010)));
    }

    @Test
    void testGetPaymentsByCustomerId() throws Exception {
        List<PaymentDTO> payments = Arrays.asList(
            new PaymentDTO()
        );

        when(paymentService.getPaymentsByCustomerId(77L)).thenReturn(payments);

        mockMvc.perform(get("/api/payments/customer/77"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].paymentType", is("Credit Card")))
            .andExpect(jsonPath("$[0].paymentStatus", is("Success")));
    }
}
