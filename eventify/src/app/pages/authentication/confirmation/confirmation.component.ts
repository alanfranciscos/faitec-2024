import { UserService } from './../../../services/user/user.service';
import { Component } from '@angular/core';
import { AuthenticationLayoutComponent } from '../../../layout/authentication-layout/authentication-layout.component';
import { ButtonComponent } from '../../../components/button/button.component';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-confirmation',
  standalone: true,
  imports: [AuthenticationLayoutComponent, ButtonComponent, FormsModule],
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.scss',
})
export class ConfirmationComponent {
  constructor(private router: Router, private userService: UserService) {}

  buttonDisaled = false;

  private getId(): number {
    const url = window.location.href;
    const split = url.split('/');
    return Number(split[split.length - 2]);
  }

  confirmationCode: string[] = ['', '', '', '', '', ''];

  onInputChange(index: number): void {
    if (
      this.confirmationCode[index] &&
      index < this.confirmationCode.length - 1
    ) {
      const nextInput = document.querySelectorAll('.confirmation-input')[
        index + 1
      ] as HTMLInputElement;
      if (nextInput) {
        nextInput.focus();
      }
    }
  }

  onKeyDown(event: KeyboardEvent, index: number): void {
    if (event.key === 'Backspace' && !this.confirmationCode[index]) {
      const previousInput = document.querySelectorAll('.confirmation-input')[
        index - 1
      ] as HTMLInputElement;
      if (previousInput) {
        previousInput.focus();
      }
    }
  }

  verifyConfirmationCode(): boolean {
    let has_all = true;
    this.confirmationCode.forEach((code) => {
      if (!code || code === '') {
        has_all = false;
      }
    });

    return !has_all && !this.buttonDisaled;
  }

  async sendConfirmationCode(): Promise<void> {
    this.buttonDisaled = true;
    let code = '';
    this.confirmationCode.forEach((c) => {
      code += c;
    });

    try {
      await this.userService.sendConfirmationCode(this.getId(), code);
      this.router.navigate(['account/login']);
    } catch (error) {
      this.buttonDisaled = false;
      return;
    }
  }
}
