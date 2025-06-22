package com.hexaware.casestudy.carrs.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.casestudy.carrs.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomerId(Long customerId);

    List<Reservation> findByCarId(Long carId);

    @Query("SELECT r.carId FROM Reservation r WHERE r.startDate <= :endDate AND r.endDate >= :startDate AND r.reservationStatus = 'Reserved'")
    List<Long> findBookedCarIds(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.reservationStatus = 'Reserved'")
    long countActiveReservations();

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.reservationStatus = 'Cancelled'")
    long countCancelledReservations();

    @Query("SELECT r.carId FROM Reservation r GROUP BY r.carId ORDER BY COUNT(r.carId) DESC")
    List<Long> findMostBookedCarId();

    @Query("SELECT c.location FROM Reservation r JOIN Car c ON r.carId = c.carId GROUP BY c.location ORDER BY COUNT(r) DESC")
    List<String> findMostActiveLocation();

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.startDate BETWEEN :start AND :end")
    long countUpcomingReservations(@org.springframework.data.repository.query.Param("start") LocalDate start,
                                   @org.springframework.data.repository.query.Param("end") LocalDate end);


    @Query(value = "SELECT AVG(DATEDIFF(end_date, start_date)) FROM reservation", nativeQuery = true)
    Double findAverageRentalDuration();

}
