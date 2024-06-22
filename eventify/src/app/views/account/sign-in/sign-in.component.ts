import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../../services/authentication.service';
import { Router, RouterModule } from '@angular/router';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserCredential } from '../../../domain/dto/user-credential';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.css'
})
export class SignInComponent implements OnInit {
  constructor(private router: Router, private authenticationService: AuthenticationService, private toastrService: ToastrService) { }

  email = new FormControl(null);
  password = new FormControl(null, [Validators.minLength(1), Validators.maxLength(10)])

  isLoginIncorrect = false;

  /**
   * Método responsável por solocitar a veerificação se existe
   * um usuário salvo no localStorage. Caso exista, a aplicação
   * é direcionada para a tela home
   */
  ngOnInit(): void {
    if (this.authenticationService.isAuthenticated()) {
      this.router.navigate(['/events/my-events']);
    }
  }

  /**
   * Método responsável por enviar email e password
   * para authenticationService fazer a validação
   * no json-server. Caso os dados estejam corretos
   * isLoginIncorrect recebe salse, se os dados
   * estiverem incorretos isLoginIncorrect recebe true
   */
  async login() {
    let credential: UserCredential = {
      email: this.email.value!,
      password: this.password.value!
    };
    try {
      await this.authenticationService.authenticate(credential);
      this.authenticationService.addCredentialsToLocalStorage(credential.email);
      await this.router.navigate(['/events/my-events']);
      this.isLoginIncorrect = false;
    } catch (e: any) {
      this.email.setValue(null);
      this.password.setValue(null);
      this.isLoginIncorrect = true;
    }
  }

  /**
   * Método responsável pela validação do botão de login
   * Se false o botão fica desabilidato
   * Se true o botão fica habilitado
   * @returns {isValid}
   */
  isFormInvalid() {
    let isValid = this.email.valid && this.password.valid;
    return isValid ? false : true;
  }
}
