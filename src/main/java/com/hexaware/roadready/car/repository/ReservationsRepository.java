package com.hexaware.roadready.car.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.roadready.car.entity.Reservations;
@Repository
public interface ReservationsRepository extends JpaRepository <Reservations,Long>{

	List<Reservations> findByStatus(String status);

	

	List<Reservations> findByUserUserId(Long userId);



	List<Reservations> findByCarCarId(Long carId);

}
