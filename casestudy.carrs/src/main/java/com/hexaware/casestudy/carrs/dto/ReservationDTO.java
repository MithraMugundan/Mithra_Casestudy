package com.hexaware.casestudy.carrs.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public class ReservationDTO {
	
	 private Long reservationId;
	 
	 @NotNull(message = "Start date is required")
	 @FutureOrPresent(message = "Start date must be today or in the future")
	 private LocalDate startDate;
	 
	 @NotNull(message = "End date is required")
	 @FutureOrPresent(message = "End date must be today or in the future")
	 private LocalDate endDate;
	 
	 @NotNull(message = "Customer ID is required")
	 private Long customerId; 
	 
	 @NotNull(message = "Car ID is required")
	 private Long carId;
	 
	 private String reservationStatus;
	 
	public ReservationDTO()
	 {
		 
	 }
	

	public ReservationDTO(Long reservationId,
			@NotNull(message = "Start date is required") @FutureOrPresent(message = "Start date must be today or in the future") LocalDate startDate,
			@NotNull(message = "End date is required") @FutureOrPresent(message = "End date must be today or in the future") LocalDate endDate,
			@NotNull(message = "Customer ID is required") Long customerId,
			@NotNull(message = "Car ID is required") Long carId, String reservationStatus) {
		super();
		this.reservationId = reservationId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.customerId = customerId;
		this.carId = carId;
		this.reservationStatus = reservationStatus;
	}


	public ReservationDTO(Long reservationId, LocalDate startDate, LocalDate endDate,
			Long customerId, Long carId) {
		super();
		this.reservationId = reservationId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.customerId = customerId;
		this.carId = carId;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getReservationStatus() {
		return reservationStatus;
	}


	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}


	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

}
