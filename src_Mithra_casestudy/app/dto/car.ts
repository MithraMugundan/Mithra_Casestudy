/**
 * Date: 09-06-2025
 * Author: Mithra M
 * Describes car-related information such as model, capacity,
 * location, availability status, and pricing.
 */


export interface CarDTO {
    carId?: number; 
    carName: string;
    year: number;
    make: string;
    carStatus: string;
    location: string;
    passengerCapacity: number;
    pricePerDay: number;
}
