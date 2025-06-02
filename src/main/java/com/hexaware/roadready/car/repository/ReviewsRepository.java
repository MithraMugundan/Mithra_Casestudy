package com.hexaware.roadready.car.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.roadready.car.entity.Reviews;

@Repository
public interface ReviewsRepository extends JpaRepository <Reviews,Long> {

	List<Reviews> findByCarCarId(Long carId);

	List<Reviews> findByUserUserId(Long userId);

}
