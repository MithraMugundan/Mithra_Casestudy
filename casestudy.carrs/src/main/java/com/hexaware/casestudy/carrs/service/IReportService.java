package com.hexaware.casestudy.carrs.service;

import java.util.Map;

public interface IReportService {

    double getTotalRevenue();

    long getTotalReservations();

    long getTotalCancelledReservations();

    String getMostActiveLocation();

    long getUpcomingReservationCount();

    Map<String, Object> getDashboardMetrics();
}
