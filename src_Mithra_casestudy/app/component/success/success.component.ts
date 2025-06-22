import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {

  reservation: any;
  payment: any;

  constructor(private router: Router) {}

  ngOnInit(): void {
    const state = history.state;
    this.reservation = state.reservation;
    this.payment = state.payment;
  }
}
