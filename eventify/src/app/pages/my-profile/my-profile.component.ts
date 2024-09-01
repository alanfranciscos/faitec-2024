import { Component } from '@angular/core';
import { ProfileComponent } from '../../layout/profile/profile.component';

@Component({
  selector: 'app-my-profile',
  standalone: true,
  imports: [ProfileComponent],
  templateUrl: './my-profile.component.html',
  styleUrl: './my-profile.component.scss',
})
export class MyProfileComponent {}
