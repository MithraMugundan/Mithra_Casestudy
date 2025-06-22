import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RentalService } from 'src/app/service/RentalService/rental.service';

@Component({
  selector: 'app-change-date',
  templateUrl: './change-date.component.html',
  styleUrls: ['./change-date.component.css']
})
export class ChangeDateComponent implements OnInit {
  reservationId!: number;
  carId!: number;
  customerId!: number;

  startDate!: string;
  endDate!: string;
  today: string = '';
  reservationStatus: string = 'Pending';

  constructor(
    private route: ActivatedRoute,
    private rentalService: RentalService,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    const currentDate = new Date();
    this.today = currentDate.toISOString().split('T')[0]; // For min attribute

    this.route.queryParams.subscribe(params => {
      this.reservationId = +params['id'] || 0;
      this.carId = +params['carId'] || 0;
      this.customerId = +params['custId'] || 0;
      this.startDate = params['start'] || '';
      this.endDate = params['end'] || '';
    });
  }

  goBack(): void {
    this.location.back();
  }

  submitChange(): void {
    const now = new Date();
    const start = new Date(this.startDate);
    const end = new Date(this.endDate);

    if (!this.startDate || !this.endDate) {
      alert('Please fill both dates.');
      return;
    }

    if (start < new Date(this.today)) {
      alert('Start date cannot be in the past.');
      return;
    }

    if (end < start) {
      alert('End date cannot be before start date.');
      return;
    }

    const updatedReservation = {
      reservationId: this.reservationId,
      carId: this.carId,
      customerId: this.customerId,
      startDate: this.startDate,
      endDate: this.endDate,
      reservationStatus: this.reservationStatus
    };

    this.rentalService.updateReservation(updatedReservation).subscribe({
      next: () => {
        alert('Reservation updated successfully');
        this.router.navigate(['/history']);
      },
      error: (err) => {
        console.error('Error updating reservation:', err);
        alert('Failed to update reservation. Please try again later.');
      }
    });
  }
}
