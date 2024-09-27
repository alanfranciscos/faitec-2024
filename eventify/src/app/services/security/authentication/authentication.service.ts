import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserCredential } from '../../../domain/dto/user-credential';
import { api, setToken, removeToken } from '../../api/api';
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

    setToken(response.data.token);
    localStorage.setItem('auth', 'true');
    return true;
  }

  logout() {
    localStorage.clear();
    removeToken();
  }

  isAuthenticated(): boolean {
    let token = localStorage.getItem('auth');

    if (token != null) {
      return true;
    }
    return false;
  }
}
