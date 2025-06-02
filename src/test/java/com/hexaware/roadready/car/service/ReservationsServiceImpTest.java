package com.hexaware.roadready.car.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ReservationsServiceImpTest {

    @Mock
    private ReservationsRepository reservationsRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private CarsRepository carsRepository;

    @InjectMocks
    private ReservationsServiceImp reservationsServiceImp;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReservation() {
        ReservationsDTO dto = new ReservationsDTO();
        dto.setUserId(1L);
        dto.setCarId(1L);
        dto.setPickupDateTime(LocalDateTime.now().plusDays(1));
        dto.setDropoffDateTime(LocalDateTime.now().plusDays(2));
        dto.setStatus("CONFIRMED");
        dto.setTotalAmount(500.0);
        dto.setCreatedAt(LocalDateTime.now());

        Users user = new Users();
        user.setUserId(1L);
        Cars car = new Cars();
        car.setCarId(1L);

        Reservations savedReservation = new Reservations();
        savedReservation.setReservationId(10L);
        savedReservation.setUser(user);
        savedReservation.setCar(car);
        savedReservation.setPickupDateTime(dto.getPickupDateTime());
        savedReservation.setDropoffDateTime(dto.getDropoffDateTime());
        savedReservation.setStatus(dto.getStatus());
        savedReservation.setTotalAmount(dto.getTotalAmount());
        savedReservation.setCreatedAt(dto.getCreatedAt());

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carsRepository.findById(1L)).thenReturn(Optional.of(car));
        when(reservationsRepository.save(any(Reservations.class))).thenReturn(savedReservation);

        Reservations result = reservationsServiceImp.addReservation(dto);

        assertNotNull(result);
        assertEquals(10L, result.getReservationId());
        assertEquals("CONFIRMED", result.getStatus());
        verify(reservationsRepository).save(any(Reservations.class));
    }

    @Test
    void testAddReservation_UserNotFound() {
        ReservationsDTO dto = new ReservationsDTO();
        dto.setUserId(99L);
        dto.setCarId(1L);

        when(usersRepository.findById(99L)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> {
            reservationsServiceImp.addReservation(dto);
        });

        assertTrue(ex.getMessage().contains("User not found"));
    }

    @Test
    void testUpdateReservation() {
        ReservationsDTO dto = new ReservationsDTO();
        dto.setReservationId(10L);
        dto.setUserId(1L);
        dto.setCarId(1L);
        dto.setPickupDateTime(LocalDateTime.now().plusDays(1));
        dto.setDropoffDateTime(LocalDateTime.now().plusDays(2));
        dto.setStatus("UPDATED");
        dto.setTotalAmount(600.0);
        dto.setCreatedAt(LocalDateTime.now());

        Users user = new Users();
        user.setUserId(1L);
        Cars car = new Cars();
        car.setCarId(1L);

        Reservations existingReservation = new Reservations();
        existingReservation.setReservationId(10L);

        when(reservationsRepository.findById(10L)).thenReturn(Optional.of(existingReservation));
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carsRepository.findById(1L)).thenReturn(Optional.of(car));
        when(reservationsRepository.save(existingReservation)).thenReturn(existingReservation);

        Reservations updated = reservationsServiceImp.updateReservation(dto);

        assertEquals("UPDATED", updated.getStatus());
        verify(reservationsRepository).save(existingReservation);
    }

    @Test
    void testCancelReservation() {
        Long reservationId = 10L;

        Reservations reservation = new Reservations();
        reservation.setReservationId(reservationId);
        reservation.setStatus("CONFIRMED");

        when(reservationsRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationsRepository.save(reservation)).thenReturn(reservation);

        String response = reservationsServiceImp.cancelReservation(reservationId);

        assertEquals("Reservation cancelled with ID: " + reservationId, response);
        assertEquals("CANCELLED", reservation.getStatus());
    }

    @Test
    void testGetReservationById() {
        Long reservationId = 10L;

        Users user = new Users();
        user.setUserId(1L);
        Cars car = new Cars();
        car.setCarId(1L);

        Reservations reservation = new Reservations();
        reservation.setReservationId(reservationId);
        reservation.setUser(user);
        reservation.setCar(car);
        reservation.setPickupDateTime(LocalDateTime.now());
        reservation.setDropoffDateTime(LocalDateTime.now().plusDays(1));
        reservation.setStatus("CONFIRMED");
        reservation.setTotalAmount(400.0);
        reservation.setCreatedAt(LocalDateTime.now());

        when(reservationsRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        ReservationsDTO dto = reservationsServiceImp.getReservationById(reservationId);

        assertEquals(reservationId, dto.getReservationId());
        assertEquals(user.getUserId(), dto.getUserId());
        assertEquals(car.getCarId(), dto.getCarId());
    }

    @Test
    void testGetReservationsByUserId() {
        Users user = new Users();
        user.setUserId(1L);

        Reservations r1 = new Reservations();
        r1.setReservationId(1L);
        r1.setUser(user);
        r1.setStatus("CONFIRMED");

        Reservations r2 = new Reservations();
        r2.setReservationId(2L);
        r2.setUser(user);
        r2.setStatus("CANCELLED");

        when(reservationsRepository.findByUserUserId(1L)).thenReturn(Arrays.asList(r1, r2));

        List<ReservationsDTO> list = reservationsServiceImp.getReservationsByUserId(1L);

        assertEquals(2, list.size());
        assertEquals("CONFIRMED", list.get(0).getStatus());
    }

    @Test
    void testGetReservationsByCarId() {
        Cars car = new Cars();
        car.setCarId(1L);

        Reservations r1 = new Reservations();
        r1.setReservationId(1L);
        r1.setCar(car);
        r1.setStatus("CONFIRMED");

        Reservations r2 = new Reservations();
        r2.setReservationId(2L);
        r2.setCar(car);
        r2.setStatus("CANCELLED");

        when(reservationsRepository.findByCarCarId(1L)).thenReturn(Arrays.asList(r1, r2));

        List<ReservationsDTO> list = reservationsServiceImp.getReservationsByCarId(1L);

        assertEquals(2, list.size());
        assertEquals("CONFIRMED", list.get(0).getStatus());
    }

    @Test
    void testGetReservationsByStatus() {
        Reservations r1 = new Reservations();
        r1.setReservationId(1L);
        r1.setStatus("CONFIRMED");

        Reservations r2 = new Reservations();
        r2.setReservationId(2L);
        r2.setStatus("CONFIRMED");

        when(reservationsRepository.findByStatus("CONFIRMED")).thenReturn(Arrays.asList(r1, r2));

        List<ReservationsDTO> list = reservationsServiceImp.getReservationsByStatus("CONFIRMED");

        assertEquals(2, list.size());
        assertEquals("CONFIRMED", list.get(0).getStatus());
    }
}
