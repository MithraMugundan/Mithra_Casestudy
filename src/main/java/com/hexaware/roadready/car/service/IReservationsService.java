package com.hexaware.roadready.car.service;

import java.util.List;

import com.hexaware.roadready.car.dto.ReservationsDTO;
import com.hexaware.roadready.car.entity.Reservations;
import com.hexaware.roadready.car.exception.ReservationNotFoundException;

public interface IReservationsService {

    Reservations addReservation(ReservationsDTO reservationDTO);

    Reservations updateReservation(ReservationsDTO reservationDTO) throws ReservationNotFoundException;

    String cancelReservation(Long reservationId) throws ReservationNotFoundException;

    ReservationsDTO getReservationById(Long reservationId) throws ReservationNotFoundException;

    List<ReservationsDTO> getReservationsByUserId(Long userId);

    // Uncomment if you want admin level access
    // List<ReservationsDTO> getAllReservations();

    List<ReservationsDTO> getReservationsByCarId(Long carId);

    List<ReservationsDTO> getReservationsByStatus(String status);
}
