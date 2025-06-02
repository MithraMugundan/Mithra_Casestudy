package com.hexaware.roadready.car.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payments {

    @Id
    
    private Long paymentId;

    @NotNull(message = "Reservation is required")
    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservations reservation;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be a positive value")
    private Double amount;

    @NotBlank(message = "Payment method is required")
    @Pattern(
        regexp = "(?i)CASH|CREDIT_CARD|DEBIT_CARD|UPI|NET_BANKING",
        message = "Payment method must be one of: CASH, CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING"
    )
    private String paymentMethod;

    @NotBlank(message = "Status is required")
    @Pattern(
        regexp = "(?i)PENDING|COMPLETED|FAILED",
        message = "Status must be PENDING, COMPLETED, or FAILED"
    )
    private String status;

    @NotNull(message = "Transaction date is required")
    private LocalDateTime transactionDate;

    // Getters and Setters

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Reservations getReservation() {
        return reservation;
    }

    public void setReservation(Reservations reservation) {
        this.reservation = reservation;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "paymentId=" + paymentId +
                ", reservation=" + reservation +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
