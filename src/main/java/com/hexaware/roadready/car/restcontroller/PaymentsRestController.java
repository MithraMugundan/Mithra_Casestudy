package com.hexaware.roadready.car.restcontroller;

import com.hexaware.roadready.car.entity.Payments;
import com.hexaware.roadready.car.exception.PaymentNotFoundException;
import com.hexaware.roadready.car.service.IPaymentsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentsRestController {

    @Autowired
    private IPaymentsService paymentsService;

    // Create a new payment
    @PostMapping
    public ResponseEntity<Payments> createPayment(@RequestBody Payments payment) {
        log.info("Received request to create payment.");
        Payments savedPayment = paymentsService.createPayment(payment);
        return ResponseEntity.ok(savedPayment);
    }

    // Get payment by paymentId
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payments> getPaymentById(@PathVariable Long paymentId) throws PaymentNotFoundException {
        log.info("Received request to fetch payment with ID: {}", paymentId);
        Payments payment = paymentsService.getPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

    // Get payment by reservationId
    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<Payments> getPaymentByReservationId(@PathVariable Long reservationId) throws PaymentNotFoundException {
        log.info("Received request to fetch payment by reservationId: {}", reservationId);
        Payments payment = paymentsService.getPaymentByReservationId(reservationId);
        return ResponseEntity.ok(payment);
    }

    // Update payment status
    @PutMapping("/{paymentId}/status")
    public ResponseEntity<Payments> updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestParam String status) throws PaymentNotFoundException {
        log.info("Received request to update status for paymentId: {}", paymentId);
        Payments updatedPayment = paymentsService.updatePaymentStatus(paymentId, status);
        return ResponseEntity.ok(updatedPayment);
    }

    // Delete payment
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable Long paymentId) throws PaymentNotFoundException {
        log.info("Received request to delete payment with ID: {}", paymentId);
        paymentsService.deletePayment(paymentId);
        return ResponseEntity.ok("Payment deleted successfully.");
    }
}
