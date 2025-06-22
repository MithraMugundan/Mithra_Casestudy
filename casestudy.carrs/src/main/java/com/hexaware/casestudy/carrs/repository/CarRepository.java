package com.hexaware.casestudy.carrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hexaware.casestudy.carrs.entity.Car;


public interface CarRepository extends JpaRepository<Car, Long>{

	 public List<Car> findByCarStatus(String status);
	 public List<Car> findByLocationAndPassengerCapacityGreaterThanEqualAndCarStatus( String location, int passengerCapacity, String status);
	 
	 @Query("SELECT c FROM Car c WHERE c.location = ?1 AND c.passengerCapacity >= ?2 AND c.id NOT IN ?3 AND c.carStatus = 'available'")
	 public List<Car> findAvailableCars(String location, int passengerCapacity, List<Long> excludedIds);

}