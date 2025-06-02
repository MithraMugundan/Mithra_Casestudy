package com.hexaware.roadready.car.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CarsDTO {

    private Long carId;

    @NotBlank(message = "Car make is required")
    @Size(max = 100, message = "Car make can't exceed 100 characters")
    private String make;

    @NotBlank(message = "Car model is required")
    @Size(max = 100, message = "Car model can't exceed 100 characters")
    private String model;

    @NotBlank(message = "Registration number is required")
    @Size(max = 50, message = "Registration number can't exceed 50 characters")
    private String registrationNumber;

    @NotBlank(message = "Fuel type is required")
    @Size(max = 50, message = "Fuel type can't exceed 50 characters")
    private String fuelType;

    @Positive(message = "Seating capacity must be positive")
    private int seatingCapacity;

    @Positive(message = "Price per day must be positive")
    private double pricePerDay;

    private boolean available;

    @Size(max = 255, message = "Description can't exceed 255 characters")
    private String description;

    public CarsDTO() {}

    public CarsDTO(Long carId, String make, String model, String registrationNumber, String fuelType,
                   int seatingCapacity, double pricePerDay, boolean available, String description) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.fuelType = fuelType;
        this.seatingCapacity = seatingCapacity;
        this.pricePerDay = pricePerDay;
        this.available = available;
        this.description = description;
    }

    // Getters and Setters

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
