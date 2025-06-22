import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthRequestDTO } from 'src/app/dto/authrequest';
import { AuthService } from 'src/app/service/AuthService/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  authRequest:AuthRequestDTO = {
    username: '',
    password: ''
  };

  successMessage: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService,private router:Router) {}

  onSubmit(loginForm: any): void {
    if (loginForm.valid) {
      this.authService.loginUser(this.authRequest).subscribe(
        (token) => {
          console.log("Token received:", token);
          localStorage.setItem('token', token); 
          const role=this.authService.getRoleFromToken();
          console.log("Role:",role )
          if(this.authService.getRoleFromToken()==='INACTIVE')
          {
            localStorage.removeItem('token');
            alert("You account has been deactivated.")
            return;
          }
          this.successMessage = 'Login successful!';
          this.errorMessage = '';
          alert(this.successMessage)
          this.router.navigate(['/showCars'])
        },
        (error) => {
          console.error("Login failed:", error);
          this.errorMessage = 'Invalid username or password.';
          this.successMessage = '';
        }
      );
    }
  }
}
