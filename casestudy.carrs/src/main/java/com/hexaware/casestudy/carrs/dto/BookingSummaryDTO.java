package com.hexaware.casestudy.carrs.dto;


public class BookingSummaryDTO {
    private Long reservationId;
    private Long customerId;
    private String userName;
    private String email;
    private String phoneNumber;
    private Long carId;
    private String carName;
    private String location;
    private Long numOfDays;
    private Double totalAmount;
    
    
    
    public Long getReservationId() {
		return reservationId;
	}



	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}



	public Long getCustomerId() {
		return customerId;
	}



	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}



	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public Long getNumOfDays() {
		return numOfDays;
	}



	public void setNumOfDays(Long numOfDays) {
		this.numOfDays = numOfDays;
	}



	public Double getTotalAmount() {
		return totalAmount;
	}



	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}



	public BookingSummaryDTO(Long reservationId, Long customerId, String userName, String email, String phoneNumber,
                             Long carId, String carName, String location, Long numOfDays, Double totalAmount) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.carId = carId;
        this.carName = carName;
        this.location = location;
        this.numOfDays = numOfDays;
        this.totalAmount = totalAmount;
    }

    // Getters & setters
}
