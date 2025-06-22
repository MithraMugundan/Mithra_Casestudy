/**
 * Date: 03-06-2025
 * Author: Mithra M
 * Description: Handles RESTful operations for car reservations such as booking,
 * updating reservation dates, canceling reservations, and viewing user bookings.
 */


package com.hexaware.casestudy.carrs.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.casestudy.carrs.dto.ReservationDTO;
import com.hexaware.casestudy.carrs.entity.UserInfo;
import com.hexaware.casestudy.carrs.service.IReservationService;

import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/reservation")
public class ReservationRestController {

	@Autowired
    IReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
        ReservationDTO reservation = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
        ReservationDTO updated = reservationService.updateReservation(reservationDTO);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservationById(reservationId);
        return new ResponseEntity<>("Reservation cancelled successfully", HttpStatus.OK);
    }

    @GetMapping("/get/{reservationId}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long reservationId) {
        ReservationDTO dto = reservationService.getReservationById(reservationId);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getbycustomer/{customerId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByCustomerId(@PathVariable Long customerId) {
        List<ReservationDTO> reservations = reservationService.getReservationsByCustomerId(customerId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/getbycar/{carId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByCarId(@PathVariable Long carId) {
        List<ReservationDTO> reservations = reservationService.getReservationsByCarId(carId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }
    
    @GetMapping("/getbookedcars")
    public ResponseEntity<List<Long>> getBookedCars(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate)
    {
    	return new ResponseEntity<List<Long>>(reservationService.findBookedCars(startDate, endDate), HttpStatus.OK);
    }
    
    @PutMapping("/checkin/{id}")
    public ResponseEntity< ReservationDTO> checkIn(@PathVariable Long id)
    {
    	return new ResponseEntity<ReservationDTO>( reservationService.checkIn(id), HttpStatus.OK);
    }
    
    @PutMapping("/checkout/{id}")
    public ResponseEntity< ReservationDTO> checkOut(@PathVariable Long id)
    {
    	return new ResponseEntity<ReservationDTO>( reservationService.checkOut(id), HttpStatus.OK);
    }
    @GetMapping("/reports/booked-customers")
    public ResponseEntity<List<UserInfo>> getBookedCustomers() {
        return ResponseEntity.ok(reservationService.getBookedCustomers());
    }

    @GetMapping("/reports/cancelled-customers")
    public ResponseEntity<List<UserInfo>> getCancelledCustomers() {
        return ResponseEntity.ok(reservationService.getCancelledCustomers());
    }

}
