import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PaymentDTO } from 'src/app/dto/payment';
import { ReservationDTO } from 'src/app/dto/reservation';
import { AuthService } from 'src/app/service/AuthService/auth.service';
import { RentalService } from 'src/app/service/RentalService/rental.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  upcomingReservations: ReservationDTO[] = [];   // status = "Reserved"
  completedReservations: ReservationDTO[] = [];  // status = "Completed"
  cancelledReservations: ReservationDTO[] = [];  // status = "Cancelled"
  paymentHistory: PaymentDTO[] = [];

  constructor(
    private rentalService: RentalService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadReservationsAndPayments();
  }

  loadReservationsAndPayments(): void {
    const userId = this.authService.getUserIdFromToken();

    if (userId) {
      this.rentalService.getReservationsByCustomerId(userId).subscribe(reservations => {
        this.upcomingReservations = reservations
          .filter(r => r.reservationStatus === 'Reserved')
          .sort((a, b) => new Date(a.startDate).getTime() - new Date(b.startDate).getTime());

        this.completedReservations = reservations
          .filter(r => r.reservationStatus === 'Completed')
          .sort((a, b) => new Date(b.endDate).getTime() - new Date(a.endDate).getTime());

        this.cancelledReservations = reservations
          .filter(r => r.reservationStatus === 'Cancelled')
          .sort((a, b) => new Date(b.endDate).getTime() - new Date(a.endDate).getTime());
      });

      this.rentalService.getPaymentsByCustomerId(userId).subscribe(payments => {
        this.paymentHistory = payments.sort(
          (a, b) => new Date(b.paymentDate).getTime() - new Date(a.paymentDate).getTime()
        );
      });
    } else {
      alert("User not logged in");
    }
  }

  goToChangeDate(reservation: ReservationDTO): void {
    this.router.navigate(['/changeDate'], {
      queryParams: {
        id: reservation.reservationId,
        carId: reservation.carId,
        custId: reservation.customerId,
        start: reservation.startDate,
        end: reservation.endDate
      }
    });
  }

  confirmCancellation(reservationId: number): void {
    const confirmed = confirm("Are you sure you want to cancel this reservation?");
    if (confirmed) {
      this.rentalService.cancelReservation(reservationId).subscribe(() => {
        alert("Reservation cancelled successfully");
        this.loadReservationsAndPayments();
      });
    }
  }

  navigateToFeedback(reservationId: number): void {
    this.router.navigate(['/feedback'], {
      state: {
        reservationId: reservationId,
        customerId: this.authService.getUserIdFromToken(),
        customerName: this.authService.getUsernameFromToken()
      }
    });
  }
}
