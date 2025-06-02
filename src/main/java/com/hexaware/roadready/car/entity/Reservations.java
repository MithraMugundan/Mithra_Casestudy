package com.hexaware.roadready.car.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservations {

    @Id
    private Long reservationId;

    @NotNull(message = "User is required")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @NotNull(message = "Car is required")
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Cars car;

    @NotNull(message = "Pickup date and time is required")
    @Future(message = "Pickup date and time must be in the future")
    private LocalDateTime pickupDateTime;

    @NotNull(message = "Dropoff date and time is required")
    @Future(message = "Dropoff date and time must be in the future")
    private LocalDateTime dropoffDateTime;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be positive")
    private Double totalAmount;

   

    @NotNull(message = "Created date is required")
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Payments payment;

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Cars getCar() {
		return car;
	}

	public void setCar(Cars car) {
		this.car = car;
	}

	public LocalDateTime getPickupDateTime() {
		return pickupDateTime;
	}

	public void setPickupDateTime(LocalDateTime pickupDateTime) {
		this.pickupDateTime = pickupDateTime;
	}

	public LocalDateTime getDropoffDateTime() {
		return dropoffDateTime;
	}

	public void setDropoffDateTime(LocalDateTime dropoffDateTime) {
		this.dropoffDateTime = dropoffDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Payments getPayment() {
		return payment;
	}

	public void setPayment(Payments payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "Reservations [reservationId=" + reservationId + ", user=" + user + ", car=" + car + ", pickupDateTime="
				+ pickupDateTime + ", dropoffDateTime=" + dropoffDateTime + ", status=" + status + ", totalAmount="
				+ totalAmount + ", createdAt=" + createdAt + "]";
	}

    
}
