import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../../services/authentication.service';
import { Router } from '@angular/router';
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { UserCredential } from '../../../domain/dto/user-credential';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.css'
})
export class SignInComponent implements OnInit {
  constructor(private router: Router, private authenticationService: AuthenticationService, private toastrService: ToastrService) {}

  email = new FormControl(null);
  password = new FormControl(null, [Validators.minLength(1), Validators.maxLength(10)])

  isLoginIncorrect = false;

  ngOnInit(): void {
    if( this.authenticationService.isAuthenticated()){
      this.router.navigate(['account/sign-in']);
    }
  }

  async login(){
    let credential: UserCredential = {
      email: this.email.value!,
      password: this.password.value!
    };
    try {
      await this.authenticationService.authenticate(credential);
      this.authenticationService.addCredentialsToLocalStorage(credential.email);
      await this.router.navigate(['/']);
      this.isLoginIncorrect = false;
    } catch (e: any) {
      // console.error(`erro: ${e}`);
      // this.toastrService.error(e.message);
      this.email.setValue(null);
      this.password.setValue(null);
      this.isLoginIncorrect = true;
    }
  }

  isFormInvalid(){
    let isValid = this.email.valid && this.password.valid;
    return isValid ? false : true;
  }
}
