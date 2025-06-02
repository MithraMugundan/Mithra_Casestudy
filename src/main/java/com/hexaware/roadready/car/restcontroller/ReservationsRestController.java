package com.hexaware.roadready.car.restcontroller;

import com.hexaware.roadready.car.dto.ReservationsDTO;
import com.hexaware.roadready.car.entity.Reservations;
import com.hexaware.roadready.car.exception.CarNotFoundException;
import com.hexaware.roadready.car.exception.ReservationNotFoundException;
import com.hexaware.roadready.car.exception.UserNotFoundException;
import com.hexaware.roadready.car.service.IReservationsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Slf4j
public class ReservationsRestController {

    @Autowired
    private IReservationsService reservationsService;

    // Create a new reservation
    @PostMapping
    public ResponseEntity<Reservations> addReservation(@RequestBody ReservationsDTO reservationDTO)
            throws UserNotFoundException, CarNotFoundException {
        log.info("API call: Add new reservation");
        Reservations reservation = reservationsService.addReservation(reservationDTO);
        return ResponseEntity.ok(reservation);
    }

    // Update an existing reservation
    @PutMapping
    public ResponseEntity<Reservations> updateReservation(@RequestBody ReservationsDTO reservationDTO)
            throws ReservationNotFoundException, UserNotFoundException, CarNotFoundException {
        log.info("API call: Update reservation");
        Reservations updated = reservationsService.updateReservation(reservationDTO);
        return ResponseEntity.ok(updated);
    }

    // Cancel a reservation
    @PutMapping("/{reservationId}/cancel")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId)
            throws ReservationNotFoundException {
        log.info("API call: Cancel reservation with ID: {}", reservationId);
        String message = reservationsService.cancelReservation(reservationId);
        return ResponseEntity.ok(message);
    }

    // Get reservation by ID
    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationsDTO> getReservationById(@PathVariable Long reservationId)
            throws ReservationNotFoundException {
        log.info("API call: Get reservation by ID");
        ReservationsDTO reservationDTO = reservationsService.getReservationById(reservationId);
        return ResponseEntity.ok(reservationDTO);
    }

    // Get reservations by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationsDTO>> getReservationsByUserId(@PathVariable Long userId) {
        log.info("API call: Get reservations by user ID");
        List<ReservationsDTO> list = reservationsService.getReservationsByUserId(userId);
        return ResponseEntity.ok(list);
    }

    // Get reservations by car ID
    @GetMapping("/car/{carId}")
    public ResponseEntity<List<ReservationsDTO>> getReservationsByCarId(@PathVariable Long carId) {
        log.info("API call: Get reservations by car ID");
        List<ReservationsDTO> list = reservationsService.getReservationsByCarId(carId);
        return ResponseEntity.ok(list);
    }

    // Get reservations by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservationsDTO>> getReservationsByStatus(@PathVariable String status) {
        log.info("API call: Get reservations by status");
        List<ReservationsDTO> list = reservationsService.getReservationsByStatus(status);
        return ResponseEntity.ok(list);
    }
}
