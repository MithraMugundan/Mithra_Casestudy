package com.hexaware.casestudy.carrs.service;

import java.time.LocalDate;
import java.util.List;

import com.hexaware.casestudy.carrs.dto.CarDTO;
import com.hexaware.casestudy.carrs.exception.CarNotFoundException;



public interface ICarService {
	
	public CarDTO addCar(CarDTO carDTO);
	
	public CarDTO getCarById(Long carId) throws CarNotFoundException;
	
	public List<CarDTO> getAllCars();
	
	public CarDTO updateCar(Long carId, CarDTO updatedCarDTO) throws CarNotFoundException;
	
	public void deleteCarById(Long carId) throws CarNotFoundException;

	public CarDTO updateVehicleStatus(Long carId, String newStatus) throws CarNotFoundException;
	public CarDTO updateCarPricing(Long carId, double newPricePerDay) throws CarNotFoundException;
	public List<CarDTO> getAvailableCars();
	public List<CarDTO> findAvailableCarsByFilter(String location, int passengerCapacity, LocalDate startDate, LocalDate endDate);
	public List<CarDTO> searchVehicles(String location, int passengerCapacity);
	boolean isCarAvailable(Long carId);


}