import { ApiService } from './../../api/api.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserCredential } from '../../../domain/dto/user-credential';

import { AuthResponse } from './types';
import { AxiosInstance } from 'axios';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private apiService: ApiService) {}

  api: AxiosInstance = this.apiService.getApi();

  async authenticate(credential: UserCredential): Promise<boolean> {
    const response = await this.api.post<AuthResponse>(
      'api/v1/account/auth/login',
      {
        email: credential.email,
        password: credential.password,
      }
    );

    if (response.status != 200) {
      return false;
    }

    localStorage.setItem('token', response.data.token);
    this.apiService.setToken(response.data.token);
    return true;
  }

  logout() {
    localStorage.clear();
    this.apiService.removeToken();
  }

  isAuthenticated(): boolean {
    let token = localStorage.getItem('token');

    if (token != null) {
      return true;
    }
    return false;
  }
}
