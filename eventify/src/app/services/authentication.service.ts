import {HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { UserCredential } from '../domain/dto/user-credential';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  async authenticate(credential: UserCredential) {
    // console.log('trying to authenticate...');
    // console.log(credential);

    let apiResponse = await firstValueFrom(this.http.get<UserCredential[]>(`http://localhost:3000/user?email=${credential.email}&password=${credential.password}`));
    console.log(apiResponse);
    if (apiResponse == null || apiResponse.length != 1) {
      throw new Error('dados invalidos');
    }
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

  addCredentialsToLocalStorage(email: string) {
    localStorage.setItem('email', email);
    localStorage.setItem('token', new Date().toLocaleTimeString());
  }
}
