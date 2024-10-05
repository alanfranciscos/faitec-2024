import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-dialog',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './dialog.component.html',
  styleUrl: './dialog.component.scss',
})
export class DialogComponent {
  @Input() dialog_title?: string;
  @Input() dialog_placeholder?: string;
  @Output() close = new EventEmitter<void>();
  closeDialog() {
    this.close.emit();
  }
}
