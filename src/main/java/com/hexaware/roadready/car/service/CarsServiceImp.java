package com.hexaware.roadready.car.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.roadready.car.dto.CarsDTO;
import com.hexaware.roadready.car.entity.Cars;
import com.hexaware.roadready.car.exception.CarNotFoundException;
import com.hexaware.roadready.car.repository.CarsRepository;

import lombok.extern.slf4j.Slf4j;


/*
 * Name        : Mithra M
 * Date        : May 24, 2025
 * Description : Implementation of car-related business logic including managing car data, retrieval, updates, and deletions.
 */

@Service
@Slf4j
public class CarsServiceImp implements ICarsService {

    @Autowired
    private CarsRepository carsRepository;

    @Override
    public Cars addCar(CarsDTO carsDTO) {
        log.info("Adding new car: {} {}", carsDTO.getMake(), carsDTO.getModel());

        Cars car = new Cars();
        car.setMake(carsDTO.getMake());
        car.setModel(carsDTO.getModel());
        car.setRegistrationNumber(carsDTO.getRegistrationNumber());
        car.setFuelType(carsDTO.getFuelType());
        car.setSeatingCapacity(carsDTO.getSeatingCapacity());
        car.setPricePerDay(carsDTO.getPricePerDay());
        car.setAvailable(carsDTO.isAvailable());
        car.setDescription(carsDTO.getDescription());

        Cars savedCar = carsRepository.save(car);
        log.info("Car added with ID: {}", savedCar.getCarId());
        return savedCar;
    }

    @Override
    public Cars updateCar(CarsDTO carsDTO) throws CarNotFoundException {
        Long carId = carsDTO.getCarId();
        log.info("Updating car with ID: {}", carId);

        Optional<Cars> optionalCar = carsRepository.findById(carId);
        if (optionalCar.isPresent()) {
            Cars car = optionalCar.get();
            car.setMake(carsDTO.getMake());
            car.setModel(carsDTO.getModel());
            car.setRegistrationNumber(carsDTO.getRegistrationNumber());
            car.setFuelType(carsDTO.getFuelType());
            car.setSeatingCapacity(carsDTO.getSeatingCapacity());
            car.setPricePerDay(carsDTO.getPricePerDay());
            car.setAvailable(carsDTO.isAvailable());
            car.setDescription(carsDTO.getDescription());

            Cars updatedCar = carsRepository.save(car);
            log.info("Car updated with ID: {}", carId);
            return updatedCar;
        } else {
            log.error("Car not found with ID: {}", carId);
            throw new CarNotFoundException("Car not found with id: " + carId);
        }
    }

    @Override
    public Cars getCarById(Long carId) throws CarNotFoundException {
        log.info("Fetching car with ID: {}", carId);
        return carsRepository.findById(carId)
                .orElseThrow(() -> {
                    log.error("Car not found with ID: {}", carId);
                    return new CarNotFoundException("Car not found with id: " + carId);
                });
    }

    @Override
    public List<Cars> getAllCars() {
        log.info("Fetching all cars");
        return carsRepository.findAll();
    }

    @Override
    public void deleteCar(Long carId) throws CarNotFoundException {
        log.info("Attempting to delete car with ID: {}", carId);
        if (!carsRepository.existsById(carId)) {
            log.warn("Car with ID {} not found. Cannot delete.", carId);
            throw new CarNotFoundException("Cannot delete. Car not found with id: " + carId);
        }
        carsRepository.deleteById(carId);
        log.info("Successfully deleted car with ID: {}", carId);
    }

}
