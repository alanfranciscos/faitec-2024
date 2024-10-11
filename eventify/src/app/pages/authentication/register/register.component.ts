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
    console.log('User registered!', {
      username: data.name,
      email: data.email,
      password: data.password,
      teste: data.profileImage,
    });

    const skip = true;

    const userId = url.split('/').pop();
    if (data.profileImage && userId && !skip) {
      this.userService.updateImageProfile(userId, data.profileImage);
    }

    const route = `/account/${userId}/confirmation`;
    this.router.navigate([route]);
  }
}
