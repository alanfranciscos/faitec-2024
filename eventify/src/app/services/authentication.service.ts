import {HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { UserCredential } from '../domain/dto/user-credential';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  /**
   * Método responsável por fazer a consulta no json-server com as credenciais
   * de email e password.
   * @param credential 
   * @returns boolean
   */
  async authenticate(credential: UserCredential) {
    try {
      const apiResponse = await firstValueFrom(this.http.get<UserCredential[]>(`http://localhost:3000/user?email=${credential.email}`));
  
      if (apiResponse && apiResponse.length === 1) {
        const user = apiResponse[0];
        // Compare a senha fornecida com a senha armazenada no banco de dados (usando bcrypt ou outra biblioteca).
        if (user.password === credential.password) {
          return true; // Autenticação bem-sucedida
        } else {
          throw new Error('Senha incorreta');
        }
      } else {
        throw new Error('Usuário não encontrado');
      }
    } catch (error) {
      console.error('Erro ao autenticar:', error);
      throw new Error('Erro ao autenticar');
    }
  }
  
  /**
   * Método responsável por apagar as credenciais de 
   * email e password do localStorage
   */
  logout() {
    localStorage.clear();
  }

  /**
   * Método responsável por verificar o token
   * no localStorage, Se houver token salvo
   * no localStorage retorna true, caso contrário
   * retorna false
   * @returns boolean
   */
  isAuthenticated(): boolean {
    let token = localStorage.getItem('token');

    if (token != null) {
      return true;
    }
    return false;
  }

  /**
   * Método responsável por salvar as credenciais
   * no localStorage
   * @param email 
   */
  addCredentialsToLocalStorage(email: string) {
    localStorage.setItem('email', email);
    localStorage.setItem('token', new Date().toLocaleTimeString());
  }
}
