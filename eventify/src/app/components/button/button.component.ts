import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

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

  constructor() {}

  onClicked: any = () => {};

  onClick(fn: any): void {
    this.onClicked = fn;
  }
}
