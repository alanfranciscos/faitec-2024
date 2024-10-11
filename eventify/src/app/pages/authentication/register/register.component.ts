import { Component } from '@angular/core';
import { ProfileComponent } from '../../../layout/profile/profile.component';
import { Route, Router, RouterLink } from '@angular/router';
import { UserInputCredential } from '../../../domain/model/user.model';
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ProfileComponent, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  constructor(private router: Router, private userService: UserService) {}

  async registerUser(data: UserInputCredential) {
    const url = await this.userService.createUserAccount(data);

    const userId = url.split('/').pop();

    const route = `/account/${userId}/confirmation`;
    this.router.navigate([route]);
  }
}
