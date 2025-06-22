/**
 * Date: 10-06-2025
 * Author: Mithra M
 * Manages car data including adding new cars, retrieving available cars,
 * editing car details, and deleting car records.
 */


import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CarDTO } from 'src/app/dto/car';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  baseUrl: string = 'http://localhost:8080/api/cars/';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', 'Bearer ' + token);
  }

  getAllCars(): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>(this.baseUrl + "get", { headers: this.getHeaders() });
  }

  getAvailableCars(): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>(this.baseUrl + "available", { headers: this.getHeaders() });
  }

  getCarById(carId: number): Observable<CarDTO> {
    return this.http.get<CarDTO>(this.baseUrl + "get/" + carId, { headers: this.getHeaders() });
  }

  addCar(car: CarDTO): Observable<CarDTO> {
    return this.http.post<CarDTO>(this.baseUrl + "add", car, { headers: this.getHeaders() });
  }

  updateCar(carId: number, car: CarDTO): Observable<CarDTO> {
    return this.http.put<CarDTO>(this.baseUrl + "update/" + carId, car, { headers: this.getHeaders() });
  }

  deleteCar(carId: number): Observable<string> {
    return this.http.delete(this.baseUrl + "delete/" + carId, { headers: this.getHeaders(), responseType: 'text' });
  }

  updateCarPrice(carId: number, price: number): Observable<CarDTO> {
    return this.http.put<CarDTO>(this.baseUrl + "updateprice/" + carId + "/" + price, null, { headers: this.getHeaders() });
  }

  updateCarStatus(carId: number, status: string): Observable<CarDTO> {
    return this.http.put<CarDTO>(this.baseUrl + "updateStatus/" + carId + "/" + status, null, { headers: this.getHeaders() });
  }

  getAvailableCarsByFilter(location: string, capacity: number, startDate: string, endDate: string): Observable<CarDTO[]> {
    return this.http.get<CarDTO[]>(`${this.baseUrl}availablecarsbyfilter?location=${location}&passengerCapacity=${capacity}&startDate=${startDate}&endDate=${endDate}`, { headers: this.getHeaders() });
  }
}
