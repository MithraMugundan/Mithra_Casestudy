package com.hexaware.roadready.car.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class ReviewsDTO {

    private Long reviewId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Car ID is required")
    private Long carId;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    @Size(max = 500, message = "Comment can't exceed 500 characters")
    private String comment;

    @NotNull(message = "Date is required")
    private LocalDate date;

    public ReviewsDTO() {}

    public ReviewsDTO(Long reviewId, Long userId, Long carId, int rating, String comment, LocalDate date) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.carId = carId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    // Getters and Setters

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
