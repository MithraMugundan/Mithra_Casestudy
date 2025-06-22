package com.hexaware.casestudy.carrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.casestudy.carrs.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    public List<Payment> findByReservationId(Long reservationId);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.reservationId IN (" +
    	       "SELECT r.reservationId FROM Reservation r WHERE r.reservationStatus NOT IN ('CANCELLED'))")
    	Double findTotalRevenue();


}
