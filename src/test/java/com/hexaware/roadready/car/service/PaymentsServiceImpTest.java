package com.hexaware.roadready.car.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import com.hexaware.roadready.car.entity.Payments;
import com.hexaware.roadready.car.exception.PaymentNotFoundException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PaymentsServiceImpTest {

    @Mock
    private IPaymentsService paymentsService;  // mock the interface or repo depending on design

    @InjectMocks
    private PaymentsServiceImp paymentsServiceImp;  // real class with mocks injected

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("Starting PaymentsServiceImp Tests...");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("PaymentsServiceImp Tests Completed.");
    }

    @org.junit.jupiter.api.BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

   
    @Test
    void testGetPaymentById() throws PaymentNotFoundException {
        Payments payment = new Payments();
        payment.setPaymentId(1001L);

        when(paymentsService.getPaymentById(1001L)).thenReturn(payment);

        Payments result = paymentsServiceImp.getPaymentById(1001L);

        assertNotNull(result);
        assertEquals(1001L, result.getPaymentId());
        verify(paymentsService).getPaymentById(1001L);
    }


}
