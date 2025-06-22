/**
 * Date: 27-05-2025
 * Author: Mithra M
 * Description: Represents the payment entity storing details like amount,
 * method, payment status, timestamp, and reservation linkage.
 * Maps to the 'payment' table in the database.
 */


package com.hexaware.casestudy.carrs.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;
	private String paymentType;
	private LocalDate paymentDate;
	private double amount;
	private String paymentStatus;
	private Long reservationId;
	
	public Payment()
	{
		
	}

	

	public Payment(Long paymentId, String paymentType, LocalDate paymentDate, double amount, String paymentStatus,
			Long reservationId) {
		super();
		this.paymentId = paymentId;
		this.paymentType = paymentType;
		this.paymentDate = paymentDate;
		this.amount = amount;
		this.paymentStatus = paymentStatus;
		this.reservationId = reservationId;
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



	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", paymentType=" + paymentType + ", paymentDate=" + paymentDate
				+ ", amount=" + amount + ", reservationId=" + reservationId + "]";
	}
	

}
