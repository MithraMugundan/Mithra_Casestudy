package com.hexaware.casestudy.carrs.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentDTO {
	
	private Long paymentId;
	
	@NotBlank(message = "Payment type is required")
	private String paymentType;
	
	private LocalDate paymentDate;
	
	@Positive(message = "Amount must be greater than zero")
	private double amount;
	
	@NotNull(message = "Reservation ID is required")
	private Long reservationId;
	
	private String paymentStatus;
	
	public PaymentDTO()
	{
		
	}

	
	public PaymentDTO(Long paymentId, @NotBlank(message = "Payment type is required") String paymentType,
			LocalDate paymentDate,
			@Positive(message = "Amount must be greater than zero") double amount,
			@NotNull(message = "Reservation ID is required") Long reservationId, String paymentStatus) {
		super();
		this.paymentId = paymentId;
		this.paymentType = paymentType;
		this.paymentDate = paymentDate;
		this.amount = amount;
		this.reservationId = reservationId;
		this.paymentStatus = paymentStatus;
	}



	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}


	public String getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
