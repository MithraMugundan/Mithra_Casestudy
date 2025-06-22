import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  username: string = '';
  newPassword: string = '';
  message: string = '';
  success: boolean = false;

  constructor(private http: HttpClient) {}

  onSubmit() {
    const payload = {
      username: this.username,
      newPassword: this.newPassword
    };

    this.http.post('http://localhost:8080/api/authenticate/reset-password', payload, { responseType: 'text' })
      .subscribe(
        (res: any) => {
          this.message = res;
          this.success = true;
        },
        (err) => {
          this.message = err.error || 'Error resetting password.';
          this.success = false;
        }
      );
  }
}
