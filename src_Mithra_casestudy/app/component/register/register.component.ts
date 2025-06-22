import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDTO } from 'src/app/dto/user';
import { AuthService } from 'src/app/service/AuthService/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{

  user: UserDTO = {
  userName: '',
  email: '',
  password: '',
  phoneNumber: '',
  roles: ''
};

successMessage:string='';
errorMessage:string='';


constructor(private authService:AuthService,private router:Router)
{

}

ngOnInit(): void {
    this.user = {
      userName: '',
      email: '',
      password: '',
      phoneNumber: '',
      roles: ''
    };
  }

  onSubmit(form: any): void {
    if (form.valid) {
      console.log('User Registered:', this.user);
      this.authService.registerUser(this.user).subscribe(
          (response) => {
          console.log("User registered successfully:", response);
          this.successMessage = 'Registration successful!';
          this.errorMessage = '';
          alert("Registration successful, now login using username and password")
          form.resetForm();  
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        },
         (error) => {
          console.error("Registration failed:", error);
          this.errorMessage = 'Registration failed. Username might already exist.';
          this.successMessage = '';
        }
      )
    }
  }

}
