package com.hexaware.roadready.car.service;



import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.roadready.car.dto.CarsDTO;
import com.hexaware.roadready.car.entity.Cars;
import com.hexaware.roadready.car.exception.CarNotFoundException;

@SpringBootTest
class CarsServiceImpTest {

    @Autowired
    private ICarsService service;

    static Long createdCarId;

    @BeforeAll
    static void setUpBeforeClass() {
        System.out.println("Starting CarsService Tests...");
    }

    @AfterAll
    static void tearDownAfterClass() {
        System.out.println("CarsService Tests Completed.");
    }

    @Test
    @DisplayName("Test Add Car")
    void testAddCar() {
        CarsDTO carsDTO = new CarsDTO();
        carsDTO.setMake("Toyota");
        carsDTO.setModel("Camry");
        carsDTO.setRegistrationNumber("TN01AB1234");
        carsDTO.setFuelType("Petrol");
        carsDTO.setSeatingCapacity(5);
        carsDTO.setPricePerDay(1500.00);
        carsDTO.setAvailable(true);
        carsDTO.setDescription("Comfortable sedan");

        Cars addedCar = service.addCar(carsDTO);
        createdCarId = addedCar.getCarId();

        assertNotNull(addedCar);
        assertEquals("Toyota", addedCar.getMake());
    }

    

    @Test
    @DisplayName("Test Get All Cars")
    void testGetAllCars() {
        List<Cars> cars = service.getAllCars();
        assertNotNull(cars);
        assertTrue(cars.size() > 0);
    }

    @Test
    @DisplayName("Test Update Car")
    void testUpdateCar() throws CarNotFoundException {
        CarsDTO carsDTO = new CarsDTO();
        carsDTO.setCarId(1L); // Make sure this ID exists
        carsDTO.setMake("Hyundai");
        carsDTO.setModel("Verna");
        carsDTO.setRegistrationNumber("TN02CD4567");
        carsDTO.setFuelType("Diesel");
        carsDTO.setSeatingCapacity(5);
        carsDTO.setPricePerDay(1800.00);
        carsDTO.setAvailable(true);
        carsDTO.setDescription("Updated description");

        Cars updatedCar = service.updateCar(carsDTO);
        assertEquals("Hyundai", updatedCar.getMake());
    }

 
}
