import { Component } from '@angular/core';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {
  cities = [
    { name: 'Mumbai', phone: '+91-93210-12345', email: 'mumbai@roadready.com' },
    { name: 'Delhi', phone: '+91-98765-43210', email: 'delhi@roadready.com' },
    { name: 'Bangalore', phone: '+91-99000-11111', email: 'bangalore@roadready.com' },
    { name: 'Hyderabad', phone: '+91-99555-66666', email: 'hyderabad@roadready.com' },
    { name: 'Chennai', phone: '+91-94444-77777', email: 'chennai@roadready.com' },
    { name: 'Kolkata', phone: '+91-93333-88888', email: 'kolkata@roadready.com' }
  ];

  onSubmit(form: any) {
    if (form.valid) {
      alert('Submitted');
      form.reset();
    }
  }
}
