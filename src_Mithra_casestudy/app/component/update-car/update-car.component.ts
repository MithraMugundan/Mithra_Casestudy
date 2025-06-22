import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { CarDTO } from 'src/app/dto/car';
import { CarService } from 'src/app/service/CarService/car.service';

@Component({
  selector: 'app-update-car',
  templateUrl: './update-car.component.html',
  styleUrls: ['./update-car.component.css']
})
export class UpdateCarComponent implements OnInit {
  car!: CarDTO;

  constructor(
    private route: ActivatedRoute,
    private carService: CarService,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const carId = params['carId'];
      if (carId) {
        this.carService.getCarById(carId).subscribe(data => {
          console.log(data);
          this.car = data;
        });
      }
    });
  }

  updateCar(): void {
    if (this.car.carId != null) {
      this.carService.updateCar(this.car.carId, this.car).subscribe(() => {
        alert('Car updated successfully');
        this.router.navigate(['/showCars']);
      });
    } else {
      alert('Car ID is missing. Cannot update.');
    }
  }

  goBack(): void {
    this.location.back();
  }
}
