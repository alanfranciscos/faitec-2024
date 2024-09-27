import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserCredential } from '../../../domain/dto/user-credential';
import { api } from '../../api/api';
import { AuthResponse } from './types';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private http: HttpClient) {}

  async authenticate(credential: UserCredential): Promise<boolean> {
    const response = await api.post<AuthResponse>('api/v1/account/auth/login', {
      email: credential.email,
      password: credential.password,
    });

    if (response.status != 200) {
      return false;
    }

    this.addCredentialsToLocalStorage(response.data.token);
    return true;
  }

  logout() {
    localStorage.clear();
  }

  isAuthenticated(): boolean {
    let token = localStorage.getItem('token');

    if (token != null) {
      return true;
    }
    return false;
  }

  /**
   * The best way to store the token not is in the local storage, but with a cookie. (csrf)
   */
  addCredentialsToLocalStorage(token: string) {
    localStorage.setItem('token', token);
  }
}
