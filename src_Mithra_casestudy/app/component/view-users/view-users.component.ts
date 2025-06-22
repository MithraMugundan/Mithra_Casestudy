import { Component, OnInit } from '@angular/core';
import { UserDTO } from 'src/app/dto/user';
import { UserService } from 'src/app/service/UserService/user.service';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.css']
})
export class ViewUsersComponent implements OnInit {

  users: UserDTO[] = [];
  filteredUsers: UserDTO[] = [];

  searchTerm: string = '';
  selectedRole: string = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getAllUsers().subscribe(
      (data) => {
        this.users = data;
        this.filteredUsers = data;
      },
      (err) => console.error('Error fetching users:', err)
    );
  }

  filterUsers(): void {
    const term = this.searchTerm.toLowerCase();

    this.filteredUsers = this.users.filter(user => {
      const matchUsername = user.userName.toLowerCase().includes(term);
      const matchRole = this.selectedRole === '' || user.roles.toLowerCase() === this.selectedRole.toLowerCase();
      return matchUsername && matchRole;
    });
  }

  deactivateUser(userId: any): void {
    if (confirm('Are you sure you want to deactivate this user?')) {
      this.userService.deactivateUser(userId).subscribe(
        () => {
          alert('User deactivated successfully');
          this.getUsers();
        },
        (err) => {
          console.error('Error deactivating user:', err);
          alert('Failed to deactivate user.');
        }
      );
    }
  }
}
