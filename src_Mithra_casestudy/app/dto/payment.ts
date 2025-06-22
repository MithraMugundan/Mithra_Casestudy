

/**
 * Date: 09-06-2025
 * Author: Mithra M
 * Contains payment details tied to a reservation including
 * amount, mode of payment, and transaction date.
 */

export interface PaymentDTO
{
    paymentId:number;
    paymentType:string;
    paymentDate:string;
    amount:number;
    reservationId:number;
    paymentStatus:string;
}