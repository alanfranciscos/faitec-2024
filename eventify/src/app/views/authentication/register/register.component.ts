import { Component, OnInit } from '@angular/core';
import {
  ValidatorFn,
  FormGroup,
  Validators,
  FormControl,
  ReactiveFormsModule,
  AbstractControl,
} from '@angular/forms';

import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ButtonComponent } from '../../../components/button/button.component';
import { AuthenticationLayoutComponent } from '../../../Layout/authentication-layout/authentication-layout.component';
import { PrimaryInputComponent } from '../../../components/primary-input/primary-input.component';
interface RegisterForm {
  name: FormControl<string | null>;
  nickname: FormControl<string | null>;
  email: FormControl<string | null>;
  state: FormControl<string | null>;
  city: FormControl<string | null>;
  password: FormControl<string | null>;
  confirmPassword: FormControl<string | null>;
  profileImage: FormControl<File | null>;
}
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PrimaryInputComponent,
    AuthenticationLayoutComponent,
    ButtonComponent,
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup<RegisterForm>;
  passwordStrength: 'weak' | 'medium' | 'strong' = 'weak';

  constructor(private router: Router) {
    this.registerForm = new FormGroup<RegisterForm>(
      {
        name: new FormControl('', [Validators.required]),
        nickname: new FormControl('', [Validators.required]),
        email: new FormControl('', [Validators.required, Validators.email]),
        state: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required]),
        password: new FormControl('', [
          Validators.required,
          Validators.minLength(6),
        ]),
        confirmPassword: new FormControl('', [
          Validators.required,
          Validators.minLength(6),
        ]),
        profileImage: new FormControl<File | null>(null),
      },
      { validators: this.passwordMatchValidator }
    );

    // Subscribe to password value changes to update password strength
    this.registerForm.get('password')?.valueChanges.subscribe((value) => {
      this.updatePasswordStrength(value);
    });
  }

  ngOnInit() {
    // No need to call setValidators here
  }

  // Custom validator to ensure passwords match
  passwordMatchValidator: ValidatorFn = (
    control: AbstractControl
  ): { [key: string]: boolean } | null => {
    const formGroup = control as FormGroup;
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;
    if (password && confirmPassword && password !== confirmPassword) {
      return { mismatch: true };
    }
    return null;
  };

  updatePasswordStrength(password: string | null) {
    if (password === null) {
      this.passwordStrength = 'weak'; // ou qualquer outro valor padrão
    } else if (password.length < 6) {
      this.passwordStrength = 'weak';
    } else if (password.length < 12) {
      this.passwordStrength = 'medium';
    } else {
      this.passwordStrength = 'strong';
    }
  }

  register() {
    if (this.registerForm.valid) {
      // Handle form submission
      const formData = new FormData();
      formData.append('name', this.registerForm.get('name')?.value || '');
      formData.append(
        'nickname',
        this.registerForm.get('nickname')?.value || ''
      );
      formData.append('email', this.registerForm.get('email')?.value || '');
      formData.append('state', this.registerForm.get('state')?.value || '');
      formData.append('city', this.registerForm.get('city')?.value || '');
      formData.append(
        'password',
        this.registerForm.get('password')?.value || ''
      );
      formData.append(
        'profileImage',
        this.registerForm.get('profileImage')?.value || ''
      );

      // Simulate form submission
      console.log('Form submitted', formData);

      // Redirect or handle after successful registration
      this.router.navigate(['login']);
    }
  }
}
