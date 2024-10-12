import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonComponent } from '../button/button.component';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dialog',
  standalone: true,
  imports: [ButtonComponent, RouterOutlet],
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

  constructor(private router: Router) {}
}
