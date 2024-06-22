import { Injectable } from '@angular/core';
import { UserCredential } from '../../domain/dto/user-credential';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserUpdateService {

  constructor(private http: HttpClient) { }
  async update(user: UserCredential) {

    return await firstValueFrom(this.http.put(`http://localhost:3000/user/${user.id}`, user));
  }
}
