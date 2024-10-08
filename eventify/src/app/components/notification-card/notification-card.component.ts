import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-notification-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification-card.component.html',
  styleUrl: './notification-card.component.scss',
})
export class NotificationCardComponent {
  @Input() cardType!: 'friend' | 'event';
  @Input() title: string = 'titulo';
  @Input() description: string = 'descripcion';
  @Input() image: string = 'imagen';
  @Input() dateNotification: string = 'startData';
}
