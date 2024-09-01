import { Injectable } from '@angular/core';
import { User } from '../../domain/model/user.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserCreateService {

  constructor(private http: HttpClient) { }

  create(user: User) {
    console.log(user);
    return this.http.post('http://localhost:3000/user', user);
  }
  
}
