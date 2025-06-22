package com.hexaware.casestudy.carrs.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CarDTO {
	
private Long carId;
	
	@NotBlank(message = "Car name is required")
    @Size(min = 2, max = 100, message = "Car name must be between 2 and 100 characters")
    private String carName;
	
	@Min(value = 1990, message = "Year must be greater than 1990")
    @Max(value = 2025, message = "Year must be lesser than 2025")
    private int year;
	
	@NotBlank(message = "Make is required")
    private String make;
	
	@NotBlank(message = "Car status is required")
    @Pattern(
        regexp = "^(Available|Rented|Maintenance)$",
        message = "Car status must be one of: Available, Rented, Maintenance"
    )
    private String carStatus;
	
	@NotBlank(message = "Location is required")
    private String location;
	
	@Min(value = 1, message = "Passenger capacity must be at least 1")
	@Max(value = 15, message = "Passenger capacity cannot exceed 15")
    private int passengerCapacity;
	
	 @DecimalMin(value = "0.0", inclusive = false, message = "Price per day must be greater than 0")
    private double pricePerDay;
    
    public CarDTO()
    {
    	
    }

	public CarDTO(Long carId, String carName, int year, String make, String carStatus, String location, int passengerCapacity,
			double pricePerDay) {
		super();
		this.carId = carId;
		this.carName = carName;
		this.year = year;
		this.make = make;
		this.carStatus = carStatus;
		this.location = location;
		this.passengerCapacity = passengerCapacity;
		this.pricePerDay = pricePerDay;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPassengerCapacity() {
		return passengerCapacity;
	}

	public void setPassengerCapacity(int passengerCapacity) {
		this.passengerCapacity = passengerCapacity;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

}