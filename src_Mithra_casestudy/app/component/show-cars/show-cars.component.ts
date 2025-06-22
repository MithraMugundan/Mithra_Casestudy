import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CarDTO } from 'src/app/dto/car';
import { AuthService } from 'src/app/service/AuthService/auth.service';
import { CarService } from 'src/app/service/CarService/car.service';

@Component({
  selector: 'app-show-cars',
  templateUrl: './show-cars.component.html',
  styleUrls: ['./show-cars.component.css']
})
export class ShowCarsComponent implements OnInit {

  customFilter = {
    location: '',
    passengerCapacity: '', 
    sortBy: '',
    searchTerm: ''
  };

  carList: CarDTO[] = [];
  filteredCars: CarDTO[] = [];
  locations: string[] = ['Chennai', 'Hyderabad', 'Bangalore', 'Delhi', 'Kolkata', 'Ahmedabad'];

  constructor(
    private carService: CarService,
    private router: Router,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.getCars();
  }

  getCars(): void {
    this.carService.getAllCars().subscribe(data => {
      this.carList = data;
      this.filteredCars = [...this.carList];
    });
  }

  applyCustomFilter(): void {
    let cars = [...this.carList];

    const { location, passengerCapacity, sortBy, searchTerm } = this.customFilter;

    if (location) {
      cars = cars.filter(c => c.location === location);
    }

    if (passengerCapacity) {
      cars = cars.filter(c => c.passengerCapacity === +passengerCapacity);
    }

    if (searchTerm.trim() !== '') {
      cars = cars.filter(c => c.carName.toLowerCase().includes(searchTerm.toLowerCase()));
    }

    if (sortBy === 'priceLowToHigh') {
      cars.sort((a, b) => a.pricePerDay - b.pricePerDay);
    } else if (sortBy === 'priceHighToLow') {
      cars.sort((a, b) => b.pricePerDay - a.pricePerDay);
    }

    this.filteredCars = cars;
  }

  onReset(): void {
    this.customFilter = {
      location: '',
      passengerCapacity: '',
      sortBy: '',
      searchTerm: ''
    };
    this.filteredCars = [...this.carList];
  }

  reserveCar(car: CarDTO): void {
    this.router.navigate(['/reserve'], {
      queryParams: {
        carId: car.carId,
        carName: car.carName,
        location: car.location,
        passengerCapacity: car.passengerCapacity,
        pricePerDay: car.pricePerDay,
        customerName: this.authService.getUsernameFromToken()
      }
    });
  }

  deleteCar(carId: number | undefined): void {
    if (carId != null && confirm('Are you sure you want to delete this car?')) {
      this.carService.deleteCar(carId).subscribe(() => {
        alert('Car deleted successfully');
        this.getCars();
      });
    }
  }

  navigateToUpdate(carId: number | undefined): void {
    if (carId != null) {
      this.router.navigate(['/updateCar'], { queryParams: { carId: carId } });
    }
  }

  getImagePath(carName: string): string {
    const name = carName.trim().toLowerCase();

    const map: { [key: string]: string } = {
      'maruti swift': 'https://stimg.cardekho.com/images/carexteriorimages/930x620/Maruti/Swift/9226/1739945896166/front-left-side-47.jpg',
      'hyundai creta': 'https://imgd.aeplcdn.com/642x336/n/cw/ec/106815/creta-exterior-right-front-three-quarter-5.jpeg?isig=0&q=80',
      'innova crysta': 'https://imgd.aeplcdn.com/664x374/n/cw/ec/51435/innova-crysta-facelift-exterior-right-front-three-quarter-2.jpeg?q=80',
      'tata nexon': 'https://imgd.aeplcdn.com/370x208/n/cw/ec/149123/nexon-ev-exterior-right-front-three-quarter-79.jpeg?isig=0&q=80',
      'honda venue': 'https://static.toiimg.com/thumb/msid-76211581,width-800,resizemode-4,imgsize-1/share.jpg',
      'toyota fortuner': 'https://upload.wikimedia.org/wikipedia/commons/6/66/2015_Toyota_Fortuner_%28New_Zealand%29.jpg',
      'kia seltos': 'https://imgd.aeplcdn.com/370x208/n/cw/ec/174323/seltos-exterior-right-front-three-quarter.jpeg?isig=0&q=80',
      'maruti baleno': 'https://www.autovista.in/assets/img/new_cars_colour_variants/new-baleno-colour-pearl-arctic-white.jpg',
      'mg hector': 'https://imgd.aeplcdn.com/370x208/n/cw/ec/174323/seltos-exterior-right-front-three-quarter.jpeg?isig=0&q=80'
    };

    return map[name] || 'https://via.placeholder.com/400x200?text=No+Image';
  }

}
