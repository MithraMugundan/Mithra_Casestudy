import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PaymentDTO } from 'src/app/dto/payment';
import { ReservationDTO } from 'src/app/dto/reservation';
import { RentalService } from 'src/app/service/RentalService/rental.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  paymentType: string = '';
  totalAmount: number = 0;
  reservationData!: ReservationDTO;

  // Card fields
  cardNumber: string = '';
  expiryDate: string = '';
  cvv: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private rentalService: RentalService
  ) {}

  ngOnInit(): void {
    const state = history.state;
    if (state && state.reservationData && state.totalAmount) {
      this.reservationData = state.reservationData;
      this.totalAmount = state.totalAmount;
    } else {
      alert('Invalid reservation details!');
      this.router.navigate(['/home']);
    }
  }

  resetCardFields(): void {
    this.cardNumber = '';
    this.expiryDate = '';
    this.cvv = '';
  }

  hasInvalidCardDetails(): boolean {
    if (this.paymentType === 'Credit Card' || this.paymentType === 'Debit Card') {
      const isCardValid = this.cardNumber.length === 16 && /^\d{16}$/.test(this.cardNumber);
      const isExpiryValid = /^(0[1-9]|1[0-2])\/\d{2}$/.test(this.expiryDate);
      const isCvvValid = this.cvv.length === 3 && /^\d{3}$/.test(this.cvv);

      return !(isCardValid && isExpiryValid && isCvvValid);
    }
    return false;
  }

  makePayment(): void {
    if (this.hasInvalidCardDetails()) {
      alert("Please enter valid card details.");
      return;
    }

    this.rentalService.createReservation(this.reservationData).subscribe({
      next: res => {
        console.log("Reservation created:", res);
        const reservationId = res.reservationId;

        const paymentDTO: PaymentDTO = {
          paymentId: 0, // backend will assign
          reservationId: reservationId,
          paymentType: this.paymentType,
          amount: this.totalAmount,
          paymentDate: new Date().toISOString(),
          paymentStatus: 'Success'
        };

        this.rentalService.makePayment(paymentDTO).subscribe({
          next: payRes => {
            this.rentalService.getReservationById(reservationId).subscribe({
              next: updatedReservation => {
                this.reservationData = updatedReservation;
                alert("Payment successful!");
                this.router.navigate(['/success'], {
                  state: { reservation: this.reservationData, payment: payRes }
                });
              },
              error: err => {
                console.error("Error fetching updated reservation", err);
              }
            });
          },
          error: err => {
            console.error("Payment failed:", err);
            alert("Payment failed. Please try again.");
          }
        });
      },
      error: err => {
        console.error("Reservation creation failed:", err);
        alert("Reservation failed. Please try again.");
      }
    });
  }
}
