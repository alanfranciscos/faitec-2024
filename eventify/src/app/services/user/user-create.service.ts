import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../domain/model/user';

@Injectable({
  providedIn: 'root'
})
export class UserCreateService {

  constructor(private http:HttpClient) { }

  create(user: User){
    return this.http.post('http://localhost:3000/user', user);
  };
}
