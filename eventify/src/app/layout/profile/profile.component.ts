import { UserInputCredential } from './../../domain/model/user.model';
import { CancelButton } from './types.d';
import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
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
  ],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  @Input() title: string = '';
  @Input() route?: string;

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

  @Output() saveInformations = new EventEmitter<UserInputCredential>();

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
        profileImage: new FormControl(null),
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

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.registerForm.patchValue({ profileImage: file });

      const reader = new FileReader();
      reader.onload = (e: ProgressEvent<FileReader>) => {
        const imageData = e.target?.result;
        this.userImage = (imageData as string) || '/assets/svg/avatar.svg';
      };
      reader.readAsDataURL(file);
    }
  }

  register() {
    if (this.registerForm.valid) {
      const data: UserInputCredential = {
        name: this.registerForm.get('name')?.value || '',
        email: this.registerForm.get('email')?.value || '',
        password: this.registerForm.get('password')?.value || '',
        profileImage: this.registerForm.get('profileImage')?.value || null,
      };

      this.saveInformations.emit(data);
    }
  }
}
