import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  totalRevenue: number = 0;
  totalReservations: number = 0;
  cancelledReservations: number = 0;
  upcomingReservations: number = 0;
  mostActiveLocation: string = '';
  averageRentalDuration: number = 0;
  errorMessage: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchTotalRevenue();
    this.fetchTotalReservations();
    this.fetchCancelledReservations();
    this.fetchUpcomingReservations();
    ;
  }

  private getAuthHeaders(): HttpHeaders {
    return new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('token'));
  }

  fetchTotalRevenue() {
    this.http.get<number>('http://localhost:8080/api/report/total-revenue', {
      headers: this.getAuthHeaders()
    }).subscribe({
      next: data => this.totalRevenue = data,
      error: () => this.errorMessage = "Failed to fetch total revenue"
    });
  }

  fetchTotalReservations() {
    this.http.get<number>('http://localhost:8080/api/report/total-reservations', {
      headers: this.getAuthHeaders()
    }).subscribe({
      next: data => this.totalReservations = data,
      error: () => this.errorMessage = "Failed to fetch total reservations"
    });
  }

  fetchCancelledReservations() {
    this.http.get<number>('http://localhost:8080/api/report/cancelled-reservations', {
      headers: this.getAuthHeaders()
    }).subscribe({
      next: data => this.cancelledReservations = data,
      error: () => this.errorMessage = "Failed to fetch cancelled reservations"
    });
  }

  fetchUpcomingReservations() {
    this.http.get<number>('http://localhost:8080/api/report/upcoming-reservations', {
      headers: this.getAuthHeaders()
    }).subscribe({
      next: data => this.upcomingReservations = data,
      error: () => this.errorMessage = "Failed to fetch upcoming reservations"
    });
  }

 

}
