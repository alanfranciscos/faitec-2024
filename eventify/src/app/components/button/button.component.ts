import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './button.component.html',
  styleUrl: './button.component.scss',
})
export class ButtonComponent {
  @Input() text: string = 'Button Text';
  @Input() isPrimary: boolean = true;
  @Input() isDisabled: boolean = false;

  @Output() clicked = new EventEmitter<void>();

  constructor() {}

  onClick(): void {
    this.clicked.emit();
  }
}
