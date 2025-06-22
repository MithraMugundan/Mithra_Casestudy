import { Component } from '@angular/core';
import { RentalService } from 'src/app/service/RentalService/rental.service';
import { ReservationDTO } from 'src/app/dto/reservation';

@Component({
  selector: 'app-checkin',
  templateUrl: './check.component.html',
  styleUrls: ['./check.component.css']
})
export class CheckComponent {
  reservationId!: number;
  customerId!: number;
  verified = false;
  checkedIn = false;
  errorMessage = '';
  actionMessage = '';
  reservation!: ReservationDTO;

  constructor(private rentalService: RentalService) {}

  verifyReservation() {
    this.errorMessage = '';
    this.actionMessage = '';

    this.rentalService.getReservationById(this.reservationId).subscribe(
      (res) => {
        if (res.customerId === this.customerId) {
          this.reservation = res;
          this.verified = true;
        } else {
          this.errorMessage = 'Customer ID does not match the reservation.';
        }
      },
       () => {
        this.errorMessage = 'Invalid Reservation ID.';
      }
    );
  }

  checkIn() {
    this.rentalService.checkIn(this.reservationId).subscribe(
      () => {
        this.checkedIn = true;
        this.actionMessage = 'Check-In successful!';
      },
       () => {
        this.actionMessage = 'Check-In failed.';
      }
    );
  }

  checkOut() {
    this.rentalService.checkOut(this.reservationId).subscribe(
       () => {
        this.actionMessage = 'Check-Out successful!';
      },
       () => {
        this.actionMessage = 'Check-Out failed.';
      }
    );
  }
}
