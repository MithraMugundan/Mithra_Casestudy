/**
 * Date: 12-06-2025
 * Author: Mithra M
 * Provides reservation and rental logic such as creating bookings,
 * updating reservation dates, retrieving reservation history,
 * and managing payment information.
 */


import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentDTO } from 'src/app/dto/payment';
import { ReservationDTO } from 'src/app/dto/reservation';

@Injectable({
  providedIn: 'root'
})
export class RentalService {

  private reservationUrl = 'http://localhost:8080/api/reservation/';
  private paymentUrl = 'http://localhost:8080/api/payment/';

  constructor(private http: HttpClient) {}

  // Helper to get auth headers with JWT
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', 'Bearer ' + token);
  }

  // ========== RESERVATION METHODS ==========

  createReservation(reservation: ReservationDTO): Observable<ReservationDTO> {
    return this.http.post<ReservationDTO>(this.reservationUrl + 'create', reservation, {
      headers: this.getHeaders()
    });
  }

  getReservationById(reservationId: number): Observable<ReservationDTO> {
    return this.http.get<ReservationDTO>(this.reservationUrl + 'get/' + reservationId, {
      headers: this.getHeaders()
    });
  }

  updateReservation(reservation: ReservationDTO): Observable<ReservationDTO> {
    return this.http.put<ReservationDTO>(this.reservationUrl + 'update', reservation, {
      headers: this.getHeaders()
    });
  }

  cancelReservation(reservationId: number): Observable<string> {
    return this.http.delete(this.reservationUrl + 'cancel/' + reservationId, {
      headers: this.getHeaders(),
      responseType: 'text'
    });
  }

  checkIn(reservationId: number): Observable<ReservationDTO> {
    return this.http.put<ReservationDTO>(this.reservationUrl + 'checkin/' + reservationId, null, {
      headers: this.getHeaders()
    });
  }

  checkOut(reservationId: number): Observable<ReservationDTO> {
    return this.http.put<ReservationDTO>(this.reservationUrl + 'checkout/' + reservationId, null, {
      headers: this.getHeaders()
    });
  }

  getReservationsByCustomerId(customerId: number): Observable<ReservationDTO[]> {
    return this.http.get<ReservationDTO[]>(this.reservationUrl + 'getbycustomer/' + customerId, {
      headers: this.getHeaders()
    });
  }

  getReservationsByCarId(carId: number): Observable<ReservationDTO[]> {
    return this.http.get<ReservationDTO[]>(this.reservationUrl + 'getbycar/' + carId, {
      headers: this.getHeaders()
    });
  }

  getAllReservations(): Observable<ReservationDTO[]> {
    return this.http.get<ReservationDTO[]>(this.reservationUrl + 'get', {
      headers: this.getHeaders()
    });
  }

  // ========== PAYMENT METHODS ==========

  makePayment(payment: PaymentDTO): Observable<PaymentDTO> {
    return this.http.post<PaymentDTO>(this.paymentUrl + 'make', payment, {
      headers: this.getHeaders()
    });
  }

  getPaymentById(paymentId: number): Observable<PaymentDTO> {
    return this.http.get<PaymentDTO>(this.paymentUrl + 'get/' + paymentId, {
      headers: this.getHeaders()
    });
  }

  getPaymentsByReservationId(reservationId: number): Observable<PaymentDTO[]> {
    return this.http.get<PaymentDTO[]>(this.paymentUrl + 'reservation/' + reservationId, {
      headers: this.getHeaders()
    });
  }

  getPaymentsByCustomerId(customerId: number): Observable<PaymentDTO[]> {
    return this.http.get<PaymentDTO[]>(this.paymentUrl + 'customer/' + customerId, {
      headers: this.getHeaders()
    });
  }
}
