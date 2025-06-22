package com.hexaware.casestudy.carrs.service;

import java.util.List;

import com.hexaware.casestudy.carrs.dto.PaymentDTO;
import com.hexaware.casestudy.carrs.exception.PaymentNotFoundException;



public interface IPaymentService {
	
	public PaymentDTO makePayment(PaymentDTO paymentDTO);
	
	public PaymentDTO getPaymentById(Long paymentId) throws PaymentNotFoundException;
	
	public List<PaymentDTO> getAllPayments();
	
	public List<PaymentDTO> getPaymentsByReservationId(Long reservationId);
	
	public List<PaymentDTO> getPaymentsByCustomerId(Long customerId); 
	
	public PaymentDTO updatePaymentStatus(Long paymentId,String status);


}
