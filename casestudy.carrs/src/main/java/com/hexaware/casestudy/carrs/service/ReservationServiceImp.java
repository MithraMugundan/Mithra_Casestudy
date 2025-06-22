
/**
 * Date: 01-06-2025
 * Author: Mithra M
 * Description: Manages reservation operations such as creating, updating,
 * fetching by customer or car, and modifying reservation status (approved/rejected).
 */

package com.hexaware.casestudy.carrs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.hexaware.casestudy.carrs.dto.PaymentDTO;
import com.hexaware.casestudy.carrs.dto.ReservationDTO;
import com.hexaware.casestudy.carrs.entity.Reservation;
import com.hexaware.casestudy.carrs.entity.UserInfo;
import com.hexaware.casestudy.carrs.exception.CarNotFoundException;
import com.hexaware.casestudy.carrs.repository.ReservationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReservationServiceImp implements IReservationService {

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	@Lazy
	ICarService carService;

	@Autowired
	@Lazy
	IPaymentService paymentService;

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        log.info("Creating reservation for customerId={}, carId={} from {} to {}", 
                reservationDTO.getCustomerId(), reservationDTO.getCarId(), 
                reservationDTO.getStartDate(), reservationDTO.getEndDate());

        if (reservationDTO.getEndDate().isBefore(reservationDTO.getStartDate())) {
            log.warn("End date must be after start date");
            throw new IllegalArgumentException("End date must be after start date");
        }

        Reservation reservation = new Reservation();
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setCustomerId(reservationDTO.getCustomerId());
        reservation.setCarId(reservationDTO.getCarId());
        reservation.setReservationStatus("Pending");

        Reservation savedReservation = reservationRepository.save(reservation);
        log.debug("Reservation saved successfully: {}", savedReservation);
        return mapToDTO(savedReservation);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        log.info("Fetching all reservations");
        List<Reservation> reservations = reservationRepository.findAll();
        log.debug("Total reservations fetched: {}", reservations.size());

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDTOs.add(mapToDTO(reservation));
        }
        return reservationDTOs;
    }

    @Override
    public ReservationDTO getReservationById(Long reservationId) {
        log.info("Fetching reservation with id={}", reservationId);
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> 
            new RuntimeException("Reservation not found"));
        return mapToDTO(reservation);
    }

    @Override
    public List<ReservationDTO> getReservationsByCustomerId(Long customerId) {
        log.info("Fetching reservations for customerId={}", customerId);
        List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
        List<ReservationDTO> dtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            dtos.add(mapToDTO(reservation));
        }
        return dtos;
    }

    @Override
    public List<ReservationDTO> getReservationsByCarId(Long carId) {
        log.info("Fetching reservations for carId={}", carId);
        List<Reservation> reservations = reservationRepository.findByCarId(carId);
        List<ReservationDTO> dtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            dtos.add(mapToDTO(reservation));
        }
        return dtos;
    }

    @Override
    public ReservationDTO updateReservation(ReservationDTO updatedDTO) {
        log.info("Updating reservation with id={}", updatedDTO.getReservationId());
        Reservation existing = reservationRepository.findById(updatedDTO.getReservationId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        existing.setStartDate(updatedDTO.getStartDate());
        existing.setEndDate(updatedDTO.getEndDate());
        existing.setCustomerId(updatedDTO.getCustomerId());
        existing.setCarId(updatedDTO.getCarId());

        return mapToDTO(reservationRepository.save(existing));
    }

    @Override
    public void cancelReservationById(Long id) {
        log.info("Cancelling reservation with id={}", id);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setReservationStatus("Cancelled");
        reservationRepository.save(reservation);

        try {
            carService.updateVehicleStatus(reservation.getCarId(), "Available");
        } catch (CarNotFoundException e) {
            log.warn("Car not found while updating status during cancel: {}", reservation.getCarId());
        }

        List<PaymentDTO> payments = paymentService.getPaymentsByReservationId(id);
        for (PaymentDTO payment : payments) {
            paymentService.updatePaymentStatus(payment.getPaymentId(), "Refunded");
        }

        log.debug("Reservation with id={} successfully cancelled", id);
    }

    @Override
    public List<Long> findBookedCars(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findBookedCarIds(startDate, endDate);
    }

    @Override
    public ReservationDTO checkIn(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setCheckInTime(LocalDateTime.now());
        return mapToDTO(reservationRepository.save(reservation));
    }

    @Override
    public ReservationDTO checkOut(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setReservationStatus("Completed");
        reservation.setCheckOutTime(LocalDateTime.now());

        try {
            carService.updateVehicleStatus(reservation.getCarId(), "Available");
        } catch (CarNotFoundException e) {
            log.warn("Car not found while updating status during check-out: {}", reservation.getCarId());
        }

        return mapToDTO(reservationRepository.save(reservation));
    }

    @Override
    public void updateReservationStatus(Long reservationId, String status) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setReservationStatus(status);
        reservationRepository.save(reservation);
    }

    private ReservationDTO mapToDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setReservationId(reservation.getReservationId());
        dto.setStartDate(reservation.getStartDate());
        dto.setEndDate(reservation.getEndDate());
        dto.setCustomerId(reservation.getCustomerId());
        dto.setCarId(reservation.getCarId());
        dto.setReservationStatus(reservation.getReservationStatus());
        
        return dto;
    }

	@Override
	public List<UserInfo> getBookedCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> getCancelledCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

   
    
}
