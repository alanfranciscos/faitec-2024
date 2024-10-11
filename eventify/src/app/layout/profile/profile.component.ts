import { CancelButton } from './types.d';
import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import {
  ValidatorFn,
  FormGroup,
  Validators,
  FormControl,
  ReactiveFormsModule,
  AbstractControl,
} from '@angular/forms';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { AuthenticationLayoutComponent } from '../authentication-layout/authentication-layout.component';
import { ButtonComponent } from '../../components/button/button.component';
import { Router, RouterLink } from '@angular/router';
import { TooltipComponent } from '../../components/tooltip/tooltip.component';

interface RegisterForm {
  name: FormControl<string | null>;
  email: FormControl<string | null>;
  password: FormControl<string | null>;
  confirmPassword: FormControl<string | null>;
  profileImage: FormControl<File | null>;
}
@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PrimaryInputComponent,
    AuthenticationLayoutComponent,
    ButtonComponent,
    RouterLink,
    TooltipComponent,
  ],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  @Input() title: string = '';
  @Input() route: string = '';

  @Input() userName: string = '';
  @Input() nickName: string = '';
  @Input() userEmail: string = '';
  @Input() userCity: string = '';
  @Input() userState: string = '';
  @Input() userImage: string = '/assets/svg/avatar.svg';
  @Input() cancelButton: CancelButton = {
    text: 'Cancelar',
    onClick: () => null,
  };

  registerForm: FormGroup<RegisterForm>;
  passwordStrength: 'weak' | 'medium' | 'strong' = 'weak';

  constructor(private router: Router) {
    this.registerForm = new FormGroup<RegisterForm>(
      {
        name: new FormControl('', [Validators.required]),
        email: new FormControl('', [Validators.required, Validators.email]),
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

    this.registerForm.get('password')?.valueChanges.subscribe((value) => {
      this.updatePasswordStrength(value);
    });
  }

  ngOnInit() {}

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
      this.passwordStrength = 'weak';
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
      const formData = new FormData();
      formData.append('name', this.registerForm.get('name')?.value || '');
      formData.append(
        'nickname',
        this.registerForm.get('nickname')?.value || ''
      );
      formData.append('email', this.registerForm.get('email')?.value || '');
      formData.append(
        'profileImage',
        this.registerForm.get('profileImage')?.value || ''
      );
    }
  }
}
