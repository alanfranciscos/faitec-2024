import { Component, OnInit } from '@angular/core';
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
import { UserCredential } from '../../../domain/dto/user-credential';
import { AuthenticationService } from '../../../services/authentication.service';
import { ToastrService } from 'ngx-toastr';

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
export class LoginComponent implements OnInit {
  loginForm: FormGroup<LoginForm>;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private toastrService: ToastrService
  ) {
    this.loginForm = new FormGroup<LoginForm>({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(10),
      ]),
    });
  }

  navigateToSignup() {
    this.router.navigate(['account/register']);
  }

  navigateToForgotPassword() {
    this.router.navigate(['account/forget-password']);
  }

  async login() {
    if (this.loginForm.invalid) {
      this.toastrService.error('Please fill in all fields correctly.');
      return;
    }
  
    const credential: UserCredential = {
      email: this.loginForm.get('email')?.value!,
      password: this.loginForm.get('password')?.value!,
    };
  
    try {
      await this.authenticationService.authenticate(credential);
      this.authenticationService.addCredentialsToLocalStorage(credential.email);
      
      // Redireciona para a página inicial ou uma página padrão
      await this.router.navigate(['/events']); // Alterar para a rota desejada após login
    } catch (e: any) {
      console.error(`erro: ${e}`);
      this.toastrService.error(e.message);
      this.loginForm.get('password')?.setValue(null);
    }
  }
  

  ngOnInit(): void {
    this.loginIfCredentialsIsValid();
  }

  loginIfCredentialsIsValid() {
    if (this.authenticationService.isAuthenticated()) {
      this.router.navigate(['/']);
    }
  }

  isFormInvalid() {
    return this.loginForm.invalid;
  }
}
