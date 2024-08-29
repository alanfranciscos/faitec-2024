import { Component } from '@angular/core';
import { PrimaryInputComponent } from '../../../components/primary-input/primary-input.component';
import { ButtonComponent } from '../../../components/button/button.component';
import { AuthenticationLayoutComponent } from '../../../Layout/authentication-layout/authentication-layout.component';

@Component({
  selector: 'app-forget-password',
  standalone: true,
  imports: [
    PrimaryInputComponent,
    ButtonComponent,
    AuthenticationLayoutComponent,
  ],
  templateUrl: './forget-password.component.html',
  styleUrl: './forget-password.component.scss',
})
export class ForgetPasswordComponent {}
