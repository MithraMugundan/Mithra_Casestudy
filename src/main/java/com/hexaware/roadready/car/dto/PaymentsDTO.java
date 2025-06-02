package com.hexaware.roadready.car.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class PaymentsDTO {

    private Long paymentId;

    @NotNull(message = "Reservation ID is required")
    private Long reservationId;

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

    public PaymentsDTO() {}

    public PaymentsDTO(Long paymentId, Long reservationId, Double amount, String paymentMethod, String status, LocalDateTime transactionDate) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    // Getters and setters

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
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
}
