package com.hexaware.roadready.car.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.roadready.car.entity.Payments;
@Repository
public interface PaymentsRepository extends JpaRepository <Payments,Long>{

	Optional<Payments> findByReservationReservationId(Long reservationId);

}
