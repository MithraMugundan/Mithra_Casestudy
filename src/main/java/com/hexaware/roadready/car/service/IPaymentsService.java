package com.hexaware.roadready.car.service;

import com.hexaware.roadready.car.entity.Payments;
import com.hexaware.roadready.car.exception.PaymentNotFoundException;

public interface IPaymentsService {

    Payments createPayment(Payments payment);

    Payments getPaymentById(Long paymentId) throws PaymentNotFoundException;

    Payments getPaymentByReservationId(Long reservationId) throws PaymentNotFoundException;

    Payments updatePaymentStatus(Long paymentId, String status) throws PaymentNotFoundException;

    void deletePayment(Long paymentId) throws PaymentNotFoundException;

}
