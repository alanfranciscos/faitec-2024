import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-notification-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification-card.component.html',
  styleUrl: './notification-card.component.scss',
})
export class NotificationCardComponent {
  @Input() title: string = 'titulo';
  @Input() description: string = 'descripcion';
  @Input() image: string = '/assets/svg/logo.svg';
  @Input() dateNotification: string = 'startData';

  @Output() accept = new EventEmitter<void>();
  @Output() decline = new EventEmitter<void>();

  onAccept() {
    this.accept.emit();
  }

  onDecline() {
    this.decline.emit();
  }
}
