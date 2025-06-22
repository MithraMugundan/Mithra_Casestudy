import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserDTO } from 'src/app/dto/user';
import { AuthService } from 'src/app/service/AuthService/auth.service';
import { UserService } from 'src/app/service/UserService/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: UserDTO = {
    userId: 0,
    userName: '',
    email: '',
    phoneNumber: '',
    roles: '',
    password: ''
  };

  isEditable: boolean = false;
  originalUser: UserDTO = { ...this.user };

  constructor(
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getUserIdFromToken();
    if (userId) {
      this.userService.getUserById(+userId).subscribe({
        next: (data) => {
          this.user = data;
          this.originalUser = { ...data };
        },
        error: (err) => {
          console.error('Error loading profile:', err);
          alert('Failed to load profile.');
        }
      });
    }
  }

  enableEdit(): void {
    this.isEditable = true;
  }

  hasChanges(): boolean {
    return this.user.userName !== this.originalUser.userName ||
           this.user.email !== this.originalUser.email ||
           this.user.phoneNumber !== this.originalUser.phoneNumber;
  }

  updateUser(): void {
    this.userService.updateUser(this.user.userId!, this.user).subscribe({
      next: (updatedUser) => {
        this.user = updatedUser;
        this.originalUser = { ...updatedUser };
        this.isEditable = false;
        alert('✅ Profile updated successfully!');
      },
      error: (err) => {
        console.error('❌ Error updating profile:', err);
        alert('Failed to update profile. Please try again later.');
      }
    });
  }
}
