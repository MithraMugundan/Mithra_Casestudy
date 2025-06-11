import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PlayersService } from 'src/app/services/players.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  data: string = '';

  teams: string[] = [
    'India',
    'Australia',
    'England',
    'New Zealand',
    'South Africa',
    'Pakistan',
    'Sri Lanka',
    'West Indies',
    'Bangladesh',
    'Afghanistan',
    'Netherlands',
    'Zimbabwe'
  ];

  constructor(private playerService: PlayersService, private router: Router) {}

  find(searchData: any) {
    const value = searchData.value.data;
    if (value) {
      this.router.navigate(['/search', value]);
      console.log('Searching player:', value);
    }
  }
}
