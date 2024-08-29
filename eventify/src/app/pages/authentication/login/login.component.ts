import { Component } from '@angular/core';
import {
  FormControl,
  ReactiveFormsModule,
  FormGroup,
  Validators,
} from '@angular/forms';
import { PrimaryInputComponent } from '../../../components/primary-input/primary-input.component';
import { AuthenticationLayoutComponent } from '../../../layout/authentication-layout/authentication-layout.component';
import { Router, RouterLink } from '@angular/router';
import { ButtonComponent } from '../../../components/button/button.component';

interface LoginForm {
  email: FormControl<string | null>;
  password: FormControl<string | null>;
}

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    PrimaryInputComponent,
    AuthenticationLayoutComponent,
    ButtonComponent,
    RouterLink,
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  loginForm!: FormGroup<LoginForm>;

  constructor(private router: Router) {
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(6),
      ]),
    });
  }

  login() {
    if (this.loginForm.valid) {
    }
  }

  navigateToSignup() {
    this.router.navigate(['account/register']);
  }

  navigateToForgotPassword() {
    this.router.navigate(['account/forget-password']);
  }
}
