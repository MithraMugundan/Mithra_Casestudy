/**
 * Date: 03-06-2025
 * Author: Mithra M
 * Description: REST controller that handles all endpoints related to car management.
 * Provides APIs for adding, updating, deleting, and listing available cars.
 */

package com.hexaware.casestudy.carrs.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.casestudy.carrs.dto.CarDTO;
import com.hexaware.casestudy.carrs.exception.CarNotFoundException;
import com.hexaware.casestudy.carrs.service.ICarService;

import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/cars")
public class CarRestController {

	@Autowired
    ICarService carService;

    @PostMapping("/add")
    public ResponseEntity<CarDTO> addCar(@RequestBody @Valid CarDTO carDTO) {
        CarDTO savedCar = carService.addCar(carDTO);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    @PutMapping("/update/{carId}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable Long carId, @RequestBody @Valid CarDTO carDTO) throws CarNotFoundException {
        CarDTO updatedCar = carService.updateCar(carId,carDTO);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @GetMapping("/get/{carId}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long carId) throws CarNotFoundException {
        CarDTO carDTO = carService.getCarById(carId);
        return new ResponseEntity<>(carDTO, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<CarDTO>> getAvailableCars() {
        return new ResponseEntity<>(carService.getAvailableCars(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable Long carId) throws CarNotFoundException {
        carService.deleteCarById(carId);
        return new ResponseEntity<>("Car deleted successfully!", HttpStatus.OK);
    }
    
    @PutMapping("/updateprice/{id}/{price}")
    public ResponseEntity<CarDTO> updateCarPrice(@PathVariable Long id, @PathVariable double price) throws CarNotFoundException
    {
    	CarDTO updatedCar = carService.updateCarPricing(id,price);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }
    
    @GetMapping("/availablecarsbyfilter")
    public ResponseEntity<List<CarDTO>> getAvailableCarsByFilter(@RequestParam String location,@RequestParam int passengerCapacity,@RequestParam LocalDate startDate,@RequestParam LocalDate endDate)
    {
    	return new ResponseEntity<List<CarDTO>>(carService.findAvailableCarsByFilter(location, passengerCapacity, startDate, endDate), HttpStatus.OK);
    }
  
    @PutMapping("/updateStatus/{carId}/{status}")
    public ResponseEntity<CarDTO> updateVehicleStatus(@PathVariable Long carId,@PathVariable String status) throws CarNotFoundException
    {
    	return new ResponseEntity<CarDTO>(carService.updateVehicleStatus(carId, status), HttpStatus.OK);
    }
    
}
