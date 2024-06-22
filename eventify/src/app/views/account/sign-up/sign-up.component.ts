import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { Router, RouterModule } from '@angular/router';
import { UserCreateService } from '../../../services/user/user-create.service';
import { UserProfile } from '../../../domain/dto/user-profile';
import { state } from '@angular/animations';
import { UserCredential } from '../../../domain/dto/user-credential';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [
    MatInputModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent implements OnInit {

  form!: FormGroup;

  fullNameMinLength: number = 2;
  fullNameMaxLength: number = 10;
  passwordMinLength: number = 2;
  passwordMaxLength: number = 10;

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private userCreateService: UserCreateService
  ) {
    this.initializeForm();
  }

  ngOnInit(): void {

  }

  initializeForm() {
    this.form = this.formBuilder.group({
      fullName: ['', [
        Validators.required,
        Validators.minLength(this.fullNameMinLength),
        Validators.maxLength(this.fullNameMaxLength)
      ]],
      email: ['', [
        Validators.required,
        Validators.email
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(this.passwordMinLength),
        Validators.maxLength(this.passwordMaxLength)
      ]],
      repeatPassword: ['', [
        Validators.required,
        Validators.minLength(this.passwordMinLength),
        Validators.maxLength(this.passwordMaxLength)
      ]],
      nickname: ['', [
        Validators.required,
      ]],
      city: ['', [
        Validators.required,
      ]],
      state: ['', [
        Validators.required,
      ]],
    });
  }


  isFormValid() {
    let isValid = this.form.controls['fullName'].valid
      && this.form.controls['email'].valid
      && this.form.controls['password'].valid
      && this.form.controls['repeatPassword'].valid
      && this.form.controls['nickname'].valid
      && this.form.controls['city'].valid
      && this.form.controls['state'].valid;

    if (this.form.controls['password'] != null
      && this.form.controls['repeatPassword'] != null
      && this.form.controls['password'].value !== this.form.controls['repeatPassword'].value) {
      return true;
    }
    return isValid ? false : true;
  };

  createAccount() {
    let user: UserCredential = {
      name: this.form.controls['fullName'].value,
      email: this.form.controls['email'].value,
      password: this.form.controls['password'].value,
      nickname: this.form.controls['nickname'].value,
      city: this.form.controls['city'].value,
      state: this.form.controls['state'].value,

    }


    this.userCreateService.create(user).subscribe({
      next: value => {
        this.router.navigate(['account/sign-in']);
      },
      error: err => {
        console.error('Erro inesperado');
      }
    })
  }
}
