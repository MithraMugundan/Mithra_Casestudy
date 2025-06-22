
/**
 * Date: 01-06-2025
 * Author: Mithra M
 * Description: Handles car management logic such as adding, editing, deleting,
 * and retrieving available cars based on location, make, model, or availability.
 */


package com.hexaware.casestudy.carrs.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.casestudy.carrs.dto.CarDTO;
import com.hexaware.casestudy.carrs.entity.Car;
import com.hexaware.casestudy.carrs.exception.CarNotFoundException;
import com.hexaware.casestudy.carrs.repository.CarRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarServiceImp implements ICarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private IReservationService reservationService;

    @Override
    public CarDTO addCar(CarDTO carDTO) {
        log.info("Adding a new car: {}", carDTO);
        Car car = new Car();
        car.setCarName(carDTO.getCarName());
        car.setMake(carDTO.getMake());
        car.setYear(carDTO.getYear());
        car.setCarStatus(carDTO.getCarStatus());
        car.setLocation(carDTO.getLocation());
        car.setPassengerCapacity(carDTO.getPassengerCapacity());
        car.setPricePerDay(carDTO.getPricePerDay());
        Car savedCar = carRepository.save(car);
        log.debug("Car saved successfully: {}", savedCar);
        return mapToDTO(savedCar);
    }

    @Override
    public CarDTO getCarById(Long carId) throws CarNotFoundException {
        log.info("Fetching car with id={}", carId);
        Car car = carRepository.findById(carId).orElse(null);
        if (car == null) {
            log.warn("Car with id={} not found", carId);
            throw new CarNotFoundException();
        }
        return mapToDTO(car);
    }

    @Override
    public List<CarDTO> getAllCars() {
        List<Car> cars = carRepository.findAll();
        List<CarDTO> carDTOs = new ArrayList<>();
        for (Car car : cars) {
            carDTOs.add(mapToDTO(car));
        }
        return carDTOs;
    }

    @Override
    public CarDTO updateCar(Long carId, CarDTO updatedCarDTO) throws CarNotFoundException {
        log.info("Updating car: {}", updatedCarDTO);
        Car existingCar = carRepository.findById(carId).orElse(null);
        if (existingCar == null) {
            log.warn("Attempted to update non-existing car with id={}", carId);
            throw new CarNotFoundException();
        }
        existingCar.setCarName(updatedCarDTO.getCarName());
        existingCar.setYear(updatedCarDTO.getYear());
        existingCar.setMake(updatedCarDTO.getMake());
        existingCar.setCarStatus(updatedCarDTO.getCarStatus());
        existingCar.setLocation(updatedCarDTO.getLocation());
        existingCar.setPassengerCapacity(updatedCarDTO.getPassengerCapacity());
        existingCar.setPricePerDay(updatedCarDTO.getPricePerDay());
        log.debug("Car updated successfully: {}", existingCar);
        return mapToDTO(carRepository.save(existingCar));
    }

    @Override
    public void deleteCarById(Long carId) throws CarNotFoundException {
        log.info("Removing car with id={}", carId);
        Car car = carRepository.findById(carId).orElse(null);
        if (car == null) {
            log.warn("Attempted to remove non-existing car with id={}", carId);
            throw new CarNotFoundException();
        }
        carRepository.deleteById(carId);
        log.debug("Car with id={} successfully removed", carId);
    }

    @Override
    public List<CarDTO> getAvailableCars() {
        log.info("Fetching all available cars");
        List<Car> cars = carRepository.findByCarStatus("Available");
        List<CarDTO> carDTOs = new ArrayList<>();
        for (Car car : cars) {
            carDTOs.add(mapToDTO(car));
        }
        return carDTOs;
    }

    @Override
    public List<CarDTO> findAvailableCarsByFilter(String location, int passengerCapacity, LocalDate startDate, LocalDate endDate) {
        log.info("Finding available cars by filter - Location: {}, Capacity: {}, Dates: {} to {}", location, passengerCapacity, startDate, endDate);
        List<Long> bookedCarIds = reservationService.findBookedCars(startDate, endDate);
        List<Car> cars = carRepository.findAvailableCars(location, passengerCapacity, bookedCarIds);
        List<CarDTO> carDTOs = new ArrayList<>();
        for (Car car : cars) {
            carDTOs.add(mapToDTO(car));
        }
        return carDTOs;
    }

    @Override
    public List<CarDTO> searchVehicles(String location, int passengerCapacity) {
        List<Car> cars = carRepository.findByLocationAndPassengerCapacityGreaterThanEqualAndCarStatus(location, passengerCapacity, "available");
        List<CarDTO> carDTOs = new ArrayList<>();
        for (Car car : cars) {
            carDTOs.add(mapToDTO(car));
        }
        return carDTOs;
    }

    @Override
    public boolean isCarAvailable(Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        return car != null && "available".equalsIgnoreCase(car.getCarStatus());
    }

    @Override
    public CarDTO updateCarPricing(Long carId, double newPricePerDay) throws CarNotFoundException {
        log.info("Updating price of car with carId {}", carId);
        Car car = carRepository.findById(carId).orElse(null);
        if (car == null) {
            log.warn("Attempted to update non-existing car with id={}", carId);
            throw new CarNotFoundException();
        }
        car.setPricePerDay(newPricePerDay);
        return mapToDTO(carRepository.save(car));
    }

    @Override
    public CarDTO updateVehicleStatus(Long carId, String newStatus) throws CarNotFoundException {
        log.info("Updating status of car with carId {}", carId);
        Car car = carRepository.findById(carId).orElseThrow(CarNotFoundException::new);
        car.setCarStatus(newStatus);
        return mapToDTO(carRepository.save(car));
    }

    private CarDTO mapToDTO(Car car) {
        CarDTO dto = new CarDTO();
        dto.setCarId(car.getCarId());
        dto.setCarName(car.getCarName());
        dto.setYear(car.getYear());
        dto.setMake(car.getMake());
        dto.setCarStatus(car.getCarStatus());
        dto.setLocation(car.getLocation());
        dto.setPassengerCapacity(car.getPassengerCapacity());
        dto.setPricePerDay(car.getPricePerDay());
        return dto;
    }
}
