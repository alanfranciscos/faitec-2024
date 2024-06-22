import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserProfile } from '../../domain/dto/user-profile';
import { UserCredential } from '../../domain/dto/user-credential';

@Injectable({
  providedIn: 'root'
})
export class UserCreateService {

  constructor(private http: HttpClient) { }

  create(user: UserCredential) {
    return this.http.post('http://localhost:3000/user', user);
  };
}
