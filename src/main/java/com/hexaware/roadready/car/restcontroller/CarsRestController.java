package com.hexaware.roadready.car.restcontroller;

import com.hexaware.roadready.car.dto.CarsDTO;
import com.hexaware.roadready.car.entity.Cars;
import com.hexaware.roadready.car.exception.CarNotFoundException;
import com.hexaware.roadready.car.service.ICarsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@Slf4j
@Validated
public class CarsRestController {

    @Autowired
    private ICarsService carsService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @PostMapping
    public ResponseEntity<Cars> addCar(@Valid @RequestBody CarsDTO carsDTO) {
        log.info("Request to add new car: {} {}", carsDTO.getMake(), carsDTO.getModel());
        Cars createdCar = carsService.addCar(carsDTO);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @PutMapping("/{carId}")
    public ResponseEntity<Cars> updateCar(@PathVariable Long carId, @Valid @RequestBody CarsDTO carsDTO) {
        log.info("Request to update car with ID: {}", carId);
        try {
            carsDTO.setCarId(carId); // Ensure DTO has the correct ID
            Cars updatedCar = carsService.updateCar(carsDTO);
            return ResponseEntity.ok(updatedCar);
        } catch (CarNotFoundException e) {
            log.error("Car not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR', 'USER')")
    @GetMapping("/{carId}")
    public ResponseEntity<Cars> getCarById(@PathVariable Long carId) {
        log.info("Request to get car with ID: {}", carId);
        try {
            Cars car = carsService.getCarById(carId);
            return ResponseEntity.ok(car);
        } catch (CarNotFoundException e) {
            log.error("Car not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR', 'USER')")
    @GetMapping
    public ResponseEntity<List<Cars>> getAllCars() {
        log.info("Request to get all cars");
        List<Cars> cars = carsService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable Long carId) {
        log.info("Request to delete car with ID: {}", carId);
        try {
            carsService.deleteCar(carId);
            return ResponseEntity.ok("Car deleted successfully");
        } catch (CarNotFoundException e) {
            log.error("Car not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");
        }
    }
}
