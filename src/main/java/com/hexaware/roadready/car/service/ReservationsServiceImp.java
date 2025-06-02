package com.hexaware.roadready.car.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.roadready.car.dto.ReservationsDTO;
import com.hexaware.roadready.car.entity.Cars;
import com.hexaware.roadready.car.entity.Reservations;
import com.hexaware.roadready.car.entity.Users;
import com.hexaware.roadready.car.exception.CarNotFoundException;
import com.hexaware.roadready.car.exception.ReservationNotFoundException;
import com.hexaware.roadready.car.exception.UserNotFoundException;
import com.hexaware.roadready.car.repository.CarsRepository;
import com.hexaware.roadready.car.repository.ReservationsRepository;
import com.hexaware.roadready.car.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

/*
 * Name        : Mithra M
 * Date        : May 27, 2025 
 * Description : Handles reservation operations including booking, cancellation, and retrieval of reservation details.
 */

@Service
@Slf4j
public class ReservationsServiceImp implements IReservationsService {

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CarsRepository carsRepository;

    @Override
    public Reservations addReservation(ReservationsDTO reservationDTO) {
        log.info("Adding reservation for userId: {}", reservationDTO.getUserId());

        Users user = usersRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + reservationDTO.getUserId()));

        Cars car = carsRepository.findById(reservationDTO.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Car not found with id: " + reservationDTO.getCarId()));

        Reservations reservation = new Reservations();
        reservation.setUser(user);
        reservation.setCar(car);
        reservation.setPickupDateTime(reservationDTO.getPickupDateTime());
        reservation.setDropoffDateTime(reservationDTO.getDropoffDateTime());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setTotalAmount(reservationDTO.getTotalAmount());
        reservation.setCreatedAt(reservationDTO.getCreatedAt());

        Reservations saved = reservationsRepository.save(reservation);
        log.info("Reservation added with ID: {}", saved.getReservationId());

        return saved;
    }

    @Override
    public Reservations updateReservation(ReservationsDTO reservationDTO) {
        Long id = reservationDTO.getReservationId();
        log.info("Updating reservation with ID: {}", id);

        Reservations existing = reservationsRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));

        Users user = usersRepository.findById(reservationDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + reservationDTO.getUserId()));

        Cars car = carsRepository.findById(reservationDTO.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Car not found with id: " + reservationDTO.getCarId()));

        existing.setUser(user);
        existing.setCar(car);
        existing.setPickupDateTime(reservationDTO.getPickupDateTime());
        existing.setDropoffDateTime(reservationDTO.getDropoffDateTime());
        existing.setStatus(reservationDTO.getStatus());
        existing.setTotalAmount(reservationDTO.getTotalAmount());
        existing.setCreatedAt(reservationDTO.getCreatedAt());

        Reservations updated = reservationsRepository.save(existing);
        log.info("Reservation updated with ID: {}", id);

        return updated;
    }

    @Override
    public String cancelReservation(Long reservationId) {
        log.info("Cancelling reservation with ID: {}", reservationId);

        Reservations reservation = reservationsRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

        reservation.setStatus("CANCELLED");
        reservationsRepository.save(reservation);

        log.info("Reservation cancelled with ID: {}", reservationId);
        return "Reservation cancelled with ID: " + reservationId;
    }

    @Override
    public ReservationsDTO getReservationById(Long reservationId) {
        log.info("Fetching reservation with ID: {}", reservationId);

        Reservations reservation = reservationsRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

        return mapToDTO(reservation);
    }

    @Override
    public List<ReservationsDTO> getReservationsByUserId(Long userId) {
        log.info("Fetching reservations for userId: {}", userId);

        List<Reservations> reservations = reservationsRepository.findByUserUserId(userId);
        return reservations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReservationsDTO> getReservationsByCarId(Long carId) {
        log.info("Fetching reservations for carId: {}", carId);

        List<Reservations> reservations = reservationsRepository.findByCarCarId(carId);
        return reservations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReservationsDTO> getReservationsByStatus(String status) {
        log.info("Fetching reservations with status: {}", status);

        List<Reservations> reservations = reservationsRepository.findByStatus(status);
        return reservations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ReservationsDTO mapToDTO(Reservations reservation) {
        ReservationsDTO dto = new ReservationsDTO();
        dto.setReservationId(reservation.getReservationId());
        dto.setUserId(reservation.getUser().getUserId());
        dto.setCarId(reservation.getCar().getCarId());
        dto.setPickupDateTime(reservation.getPickupDateTime());
        dto.setDropoffDateTime(reservation.getDropoffDateTime());
        dto.setStatus(reservation.getStatus());
        dto.setTotalAmount(reservation.getTotalAmount());
        dto.setCreatedAt(reservation.getCreatedAt());
        return dto;
    }
}
