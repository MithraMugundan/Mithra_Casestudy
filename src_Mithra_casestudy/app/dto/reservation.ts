/**
 * Date: 09-06-2025
 * Author: Mithra M
 * Captures booking details for a reservation including customer,
 * car, dates, and approval status.
 */



export interface ReservationDTO{
    reservationId:number;
	startDate:string;
	endDate:string;
	customerId:number; 
	carId:number;
	reservationStatus:string;
}     