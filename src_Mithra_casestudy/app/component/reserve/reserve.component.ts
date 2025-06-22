import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/service/AuthService/auth.service';

@Component({
  selector: 'app-reserve',
  templateUrl: './reserve.component.html',
  styleUrls: ['./reserve.component.css']
})
export class ReserveComponent implements OnInit {
  reservationDetails = {
    carName: '',
    carId: 0,
    customerName: '',
    location: '',
    passengerCapacity: 0,
    startDate: '',
    endDate: '',
    pricePerDay: 0,
    totalAmount: 0
  };

  minDate = '';
  displayFields: { label: string, value: any }[] = [];

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private location: Location,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const today = new Date();
    this.minDate = today.toISOString().split('T')[0];

    this.activatedRoute.queryParams.subscribe(params => {
      this.reservationDetails = {
        carName: params['carName'],
        carId: Number(params['carId']),
        customerName: params['customerName'],
        location: params['location'],
        passengerCapacity: Number(params['passengerCapacity']),
        startDate: '',
        endDate: '',
        pricePerDay: Number(params['pricePerDay']),
        totalAmount: 0
      };

      this.displayFields = [
        { label: 'Car ID', value: this.reservationDetails.carId },
        { label: 'Car Name', value: this.reservationDetails.carName },
        { label: 'Customer Name', value: this.reservationDetails.customerName },
        { label: 'Location', value: this.reservationDetails.location },
        { label: 'Max Capacity', value: this.reservationDetails.passengerCapacity }
      ];
    });
  }

  calculateTotalAmount(): void {
    const { startDate, endDate, pricePerDay } = this.reservationDetails;
    if (startDate && endDate) {
      const start = new Date(startDate);
      const end = new Date(endDate);

      if (end <= start) {
        alert("End date must be after start date.");
        this.reservationDetails.endDate = '';
        this.reservationDetails.totalAmount = 0;
        return;
      }

      const diffDays = Math.ceil((end.getTime() - start.getTime()) / (1000 * 3600 * 24));
      this.reservationDetails.totalAmount = diffDays * pricePerDay;
    } else {
      this.reservationDetails.totalAmount = 0;
    }
  }

  goBack(): void {
    this.location.back();
  }

  pay(): void {
    const userId = this.authService.getUserIdFromToken();

    if (!this.reservationDetails.startDate || !this.reservationDetails.endDate) {
      alert('Please select both start and end dates.');
      return;
    }

    this.router.navigate(['/pay'], {
      state: {
        reservationData: {
          carId: this.reservationDetails.carId,
          customerId: userId,
          startDate: this.reservationDetails.startDate,
          endDate: this.reservationDetails.endDate
        },
        totalAmount: this.reservationDetails.totalAmount
      }
    });
    alert('You will be redirected to the payment page.');
  }
}
