import { Component } from '@angular/core';
import { PrimaryInputComponent } from '../../../components/primary-input/primary-input.component';

import { AuthenticationLayoutComponent } from '../../../layout/authentication-layout/authentication-layout.component';
import { ButtonComponent } from '../../../components/button/button.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-forget-password',
  standalone: true,
  imports: [
    PrimaryInputComponent,
    ButtonComponent,
    AuthenticationLayoutComponent,
    ButtonComponent,
    RouterLink,
  ],
  templateUrl: './forget-password.component.html',
  styleUrl: './forget-password.component.scss',
})
export class ForgetPasswordComponent {}
