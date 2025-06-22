/**
 * Date: 01-06-2025
 * Author: Mithra M
 * Description: Contains business logic for processing and verifying payments,
 * linking payments to reservations, and updating payment status.
 */


package com.hexaware.casestudy.carrs.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.casestudy.carrs.dto.PaymentDTO;
import com.hexaware.casestudy.carrs.dto.ReservationDTO;
import com.hexaware.casestudy.carrs.entity.Payment;
import com.hexaware.casestudy.carrs.entity.Reservation;
import com.hexaware.casestudy.carrs.exception.CarNotFoundException;
import com.hexaware.casestudy.carrs.exception.PaymentNotFoundException;
import com.hexaware.casestudy.carrs.repository.PaymentRepository;
import com.hexaware.casestudy.carrs.repository.ReservationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImp implements IPaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    IReservationService reservationService;

    @Autowired
    CarServiceImp carService; 
    
    @Override
    public PaymentDTO makePayment(PaymentDTO paymentDTO) {
        log.info("Creating payment for reservationId={}, amount={} on {}",
                paymentDTO.getReservationId(), paymentDTO.getAmount(), LocalDate.now());

        if (!reservationRepository.existsById(paymentDTO.getReservationId())) {
            log.warn("No reservation with reservation id: {}", paymentDTO.getReservationId());
            throw new RuntimeException("Reservation Id not found");
        }

        Payment payment = new Payment();
        payment.setPaymentType(paymentDTO.getPaymentType());
        payment.setAmount(paymentDTO.getAmount());
        payment.setReservationId(paymentDTO.getReservationId());
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentStatus("Pending");

        reservationService.updateReservationStatus(paymentDTO.getReservationId(), "Reserved");

        ReservationDTO reservation = reservationService.getReservationById(paymentDTO.getReservationId());

        try {
            carService.updateVehicleStatus(reservation.getCarId(), "Rented");
        } catch (CarNotFoundException e) {
            log.error("Car not found when updating status to Rented for carId={}", reservation.getCarId());
            throw new RuntimeException("Car not found during payment processing.");
        }

        payment.setPaymentStatus("Successful");

        Payment savedPayment = paymentRepository.save(payment);
        log.debug("Payment saved successfully: {}", savedPayment);
        return mapToDTO(savedPayment);
    }

    @Override
    public PaymentDTO getPaymentById(Long paymentId) throws PaymentNotFoundException {
        log.info("Fetching payment with paymentId={}", paymentId);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(PaymentNotFoundException::new);
        return mapToDTO(payment);
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        log.info("Fetching all payments");
        List<Payment> payments = paymentRepository.findAll();
        log.debug("Total payments fetched: {}", payments.size());

        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        for (Payment payment : payments) {
            paymentDTOs.add(mapToDTO(payment));
        }
        return paymentDTOs;
    }

    @Override
    public List<PaymentDTO> getPaymentsByReservationId(Long reservationId) {
        log.info("Fetching payments for reservationId={}", reservationId);
        List<Payment> payments = paymentRepository.findByReservationId(reservationId);
        log.debug("Found {} payments for reservationId={}", payments.size(), reservationId);

        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        for (Payment payment : payments) {
            paymentDTOs.add(mapToDTO(payment));
        }
        return paymentDTOs;
    }

    @Override
    public List<PaymentDTO> getPaymentsByCustomerId(Long customerId) {
        log.info("Fetching payments for customerId={}", customerId);
        List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
        List<PaymentDTO> customerPayments = new ArrayList<>();

        for (Reservation reservation : reservations) {
            List<PaymentDTO> payments = getPaymentsByReservationId(reservation.getReservationId());
            customerPayments.addAll(payments);
        }

        log.debug("Found {} payments for customerId={}", customerPayments.size(), customerId);
        return customerPayments;
    }

    @Override
    public PaymentDTO updatePaymentStatus(Long paymentId, String status) {
        log.info("Updating payment status for paymentId={} to {}", paymentId, status);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setPaymentStatus(status);
        return mapToDTO(paymentRepository.save(payment));
    }

    private PaymentDTO mapToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setPaymentType(payment.getPaymentType());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setReservationId(payment.getReservationId());
        dto.setPaymentStatus(payment.getPaymentStatus());
        return dto;
    }
}
