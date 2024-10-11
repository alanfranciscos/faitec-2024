import { Component } from '@angular/core';
import { AuthenticationLayoutComponent } from '../../../layout/authentication-layout/authentication-layout.component';
import { ButtonComponent } from '../../../components/button/button.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-confirmation',
  standalone: true,
  imports: [AuthenticationLayoutComponent, ButtonComponent, FormsModule],
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.scss',
})
export class ConfirmationComponent {
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
}
