import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CarService } from 'src/app/service/CarService/car.service';
import { CarDTO } from 'src/app/dto/car';

@Component({
  selector: 'app-add-car',
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.css']
})
export class AddCarComponent {
  car: CarDTO = {
    carName: '',
    year: 0,
    make: '',
    carStatus: '',
    location: '',
    passengerCapacity: 0,
    pricePerDay: 0
    
  };

  constructor(
    private carService: CarService,
    private router: Router,
    private location: Location
  ) {}

  goBack(): void {
    this.location.back();
  }

  onSubmit() {
    this.carService.addCar(this.car).subscribe(
      (res) => {
        console.log("Car added:", res);
        alert('Car added successfully!');
        this.router.navigate(['/showCars']);
      },
      (err) => {
        console.error('Error adding car:', err);
        alert('Failed to add car.');
      }
    );
  }
}
