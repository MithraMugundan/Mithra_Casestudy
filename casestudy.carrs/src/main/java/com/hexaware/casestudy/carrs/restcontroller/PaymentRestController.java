
/**
 * Date: 03-06-2025
 * Author: Mithra M
 * Description: Exposes REST endpoints for processing payments, retrieving payment
 * history by user or reservation, and updating payment status.
 */

package com.hexaware.casestudy.carrs.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.casestudy.carrs.dto.PaymentDTO;
import com.hexaware.casestudy.carrs.exception.PaymentNotFoundException;
import com.hexaware.casestudy.carrs.service.IPaymentService;

import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/payment")
public class PaymentRestController {

	@Autowired
    IPaymentService paymentService;

    @PostMapping("/make")
    public ResponseEntity<PaymentDTO> makePayment(@RequestBody @Valid PaymentDTO paymentDTO) {
        PaymentDTO payment = paymentService.makePayment(paymentDTO);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @GetMapping("/get/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId) throws PaymentNotFoundException {
        PaymentDTO dto = paymentService.getPaymentById(paymentId);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByReservationId(@PathVariable Long reservationId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByReservationId(reservationId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByCustomerId(@PathVariable Long customerId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByCustomerId(customerId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}
