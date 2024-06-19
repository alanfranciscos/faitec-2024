import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../../services/authentication.service';
import { Router } from '@angular/router';
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { UserCredential } from '../../../domain/dto/user-credential';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.css'
})
export class SignInComponent implements OnInit {
  constructor(private router: Router, private authenticationService: AuthenticationService) {}

  email = new FormControl(null);
  password = new FormControl(null, [Validators.minLength(1), Validators.maxLength(10)])

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
    console.log(credential);
  }
}
