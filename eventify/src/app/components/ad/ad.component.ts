import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonComponent } from '../button/button.component';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dialog',
  standalone: true,
  imports: [ButtonComponent, RouterOutlet],
  templateUrl: './ad.component.html',
  styleUrl: './ad.component.scss',
})
export class AdComponent {
  @Input() dialog_title?: string;
  @Input() dialog_placeholder?: string;
  @Output() close = new EventEmitter<void>();
  closeDialog() {
    this.close.emit();
  }

  constructor(private router: Router) {}
}
