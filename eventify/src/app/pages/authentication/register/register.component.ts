import { Component } from '@angular/core';
import { ProfileComponent } from '../../../layout/profile/profile.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ProfileComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {}
