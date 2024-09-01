import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserCredential } from '../../domain/dto/user-credential';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserReadService {

  constructor(private http: HttpClient) { }
  // CRUD - create - read - update - delete

  findById(id: string): Promise<UserCredential> {
    return firstValueFrom(this.http.get<UserCredential>(`http://localhost:3000/user/${id}`));
  }

  findByName(name: string): Promise<UserCredential[]> {
    return firstValueFrom(this.http.get<UserCredential[]>(`http://localhost:3000/user?name=${name}`));
  }

  findAll(): Promise<UserCredential[]> {
    return firstValueFrom(this.http.get<UserCredential[]>('http://localhost:3000/user'));
  }
}
