
/**
 * Date: 03-06-2025
 * Author: Mithra M
 * Description: Controller for generating administrative reports including total
 * revenue, number of reservations, customer booking stats, and car-wise data.
 */

package com.hexaware.casestudy.carrs.restcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.casestudy.carrs.service.IReportService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/report")
public class ReportRestController {

    @Autowired
    private IReportService reportService;

    
    @GetMapping("/total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        return new ResponseEntity<>(reportService.getTotalRevenue(), HttpStatus.OK);
    }

    @GetMapping("/total-reservations")
    public ResponseEntity<Long> getTotalReservations() {
        return new ResponseEntity<>(reportService.getTotalReservations(), HttpStatus.OK);
    }

    @GetMapping("/cancelled-reservations")
    public ResponseEntity<Long> getCancelledReservations() {
        return new ResponseEntity<>(reportService.getTotalCancelledReservations(), HttpStatus.OK);
    }

    @GetMapping("/most-active-location")
    public ResponseEntity<String> getMostActiveLocation() {
        return new ResponseEntity<>(reportService.getMostActiveLocation(), HttpStatus.OK);
    }

    @GetMapping("/upcoming-reservations")
    public ResponseEntity<Long> getUpcomingReservations() {
        return new ResponseEntity<>(reportService.getUpcomingReservationCount(), HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardMetrics() {
        return new ResponseEntity<>(reportService.getDashboardMetrics(), HttpStatus.OK);
    }
}
