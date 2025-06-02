package com.hexaware.roadready.car.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.roadready.car.entity.Payments;
import com.hexaware.roadready.car.exception.PaymentNotFoundException;
import com.hexaware.roadready.car.repository.PaymentsRepository;

import lombok.extern.slf4j.Slf4j;

/*
 * Name        : Mithra
 * Date        : May 29, 2025
 * Description : Implementation of payment processing, handling transactions, payment validation, and records.
 */

@Service
@Slf4j
public class PaymentsServiceImp implements IPaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Override
    public Payments createPayment(Payments payment) {
        log.info("Creating new payment for reservationId: {}", payment.getReservation().getReservationId());
        Payments savedPayment = paymentsRepository.save(payment);
        log.info("Payment created with ID: {}", savedPayment.getPaymentId());
        return savedPayment;
    }

    @Override
    public Payments getPaymentById(Long paymentId) throws PaymentNotFoundException {
        log.info("Fetching payment with ID: {}", paymentId);
        return paymentsRepository.findById(paymentId)
            .orElseThrow(() -> {
                log.error("Payment not found with ID: {}", paymentId);
                return new PaymentNotFoundException("Payment not found with id: " + paymentId);
            });
    }

    @Override
    public Payments getPaymentByReservationId(Long reservationId) throws PaymentNotFoundException {
        log.info("Fetching payment for reservationId: {}", reservationId);
        return paymentsRepository.findByReservationReservationId(reservationId)
            .orElseThrow(() -> {
                log.error("Payment not found for reservationId: {}", reservationId);
                return new PaymentNotFoundException("Payment not found for reservation id: " + reservationId);
            });
    }

    @Override
    public Payments updatePaymentStatus(Long paymentId, String status) throws PaymentNotFoundException {
        log.info("Updating payment status for paymentId: {}", paymentId);
        Payments payment = paymentsRepository.findById(paymentId)
            .orElseThrow(() -> {
                log.error("Payment not found with ID: {}", paymentId);
                return new PaymentNotFoundException("Payment not found with id: " + paymentId);
            });
        payment.setStatus(status);
        Payments updatedPayment = paymentsRepository.save(payment);
        log.info("Payment status updated for paymentId: {}", paymentId);
        return updatedPayment;
    }

    @Override
    public void deletePayment(Long paymentId) throws PaymentNotFoundException {
        log.info("Deleting payment with ID: {}", paymentId);
        if (!paymentsRepository.existsById(paymentId)) {
            log.error("Payment not found with ID: {}", paymentId);
            throw new PaymentNotFoundException("Payment not found with id: " + paymentId);
        }
        paymentsRepository.deleteById(paymentId);
        log.info("Payment deleted with ID: {}", paymentId);
    }
}
