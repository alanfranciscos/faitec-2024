import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserCredential } from '../../domain/dto/user-credential';

@Injectable({
  providedIn: 'root'
})
export class UserDeleteService {

  constructor(private http: HttpClient) { }

  delete(user: UserCredential) {
    const url = `http://localhost:3000/user/${user.id}`;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      body: user
    }
    return this.http.delete(url, httpOptions);
  };
}
