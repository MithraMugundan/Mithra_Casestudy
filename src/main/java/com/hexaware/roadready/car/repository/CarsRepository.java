package com.hexaware.roadready.car.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.roadready.car.entity.Cars;
@Repository
public interface CarsRepository extends JpaRepository <Cars,Long>{

}
