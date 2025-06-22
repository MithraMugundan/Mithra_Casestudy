
/**
 * Date: 01-06-2025
 * Author: Mithra M
 * Description: Generates admin-level reports including revenue summaries,
 * customer booking statistics, and car-wise booking frequency.
 */

package com.hexaware.casestudy.carrs.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.casestudy.carrs.repository.PaymentRepository;
import com.hexaware.casestudy.carrs.repository.ReservationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReportServiceImp implements IReportService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public double getTotalRevenue() {
        Double revenue = paymentRepository.findTotalRevenue();
        return (revenue != null) ? revenue : 0.0;
    }

    @Override
    public long getTotalReservations() {
        return reservationRepository.countActiveReservations();
    }

    @Override
    public long getTotalCancelledReservations() {
        return reservationRepository.countCancelledReservations();
    }

    @Override
    public String getMostActiveLocation() {
        var locations = reservationRepository.findMostActiveLocation();
        return locations != null && !locations.isEmpty() ? locations.get(0) : "N/A";
    }

    @Override
    public long getUpcomingReservationCount() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        return reservationRepository.countUpcomingReservations(today, nextWeek);
    }


    @Override
    public Map<String, Object> getDashboardMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalRevenue", getTotalRevenue());
        metrics.put("totalReservations", getTotalReservations());
        metrics.put("cancelledReservations", getTotalCancelledReservations());
        metrics.put("mostActiveLocation", getMostActiveLocation());
        metrics.put("upcomingReservations", getUpcomingReservationCount());
        return metrics;
    }
}
