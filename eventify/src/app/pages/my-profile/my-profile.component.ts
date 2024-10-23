import { Component, OnInit } from '@angular/core';
import { ProfileComponent } from '../../layout/profile/profile.component';
import { UserService } from '../../services/user/user.service';
import { UserInputCredential } from './../../domain/model/user.model';
import { Router } from '@angular/router';
import { CancelButton } from '../../layout/profile/types';
import { ProfileData } from '../../domain/model/event/profileData.model';

@Component({
  selector: 'app-my-profile',
  standalone: true,
  imports: [ProfileComponent],
  templateUrl: './my-profile.component.html',
  styleUrl: './my-profile.component.scss',
})
export class MyProfileComponent implements OnInit {
  constructor(private router: Router, private userService: UserService) {}

  userName!: string;
  image: string = '../../../assets/svg/logo.svg';

  userEmail!: string;
  cancelButton: CancelButton = {
    text: 'Cancelar',
    route: '/',
  };

  dataProfile!: ProfileData;

  async ngOnInit(): Promise<void> {
    this.dataProfile = await this.userService.getProfileData();
    this.userName = this.dataProfile.username;
    if (this.dataProfile.imageData) {
      this.image = this.dataProfile.imageData;
    }
    this.userEmail = this.dataProfile.email;
  }

  async saveInformations(input: UserInputCredential) {
    console.log(input);
    await this.userService.updateProfileData(input.name, input.password, null);

    this.router.navigate(['/']);
  }
}
