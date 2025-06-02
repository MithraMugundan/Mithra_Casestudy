package com.hexaware.roadready.car.service;

import java.util.List;


import com.hexaware.roadready.car.dto.CarsDTO;
import com.hexaware.roadready.car.entity.Cars;
import com.hexaware.roadready.car.exception.CarNotFoundException;

public interface ICarsService {

    Cars addCar(CarsDTO carDTO);

    Cars updateCar(CarsDTO carDTO) throws CarNotFoundException;

    Cars getCarById(Long carId) throws CarNotFoundException;

    List<Cars> getAllCars();

    void deleteCar(Long carId) throws CarNotFoundException;
}
