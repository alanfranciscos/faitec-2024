import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-friend-card',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './friend-card.component.html',
  styleUrl: './friend-card.component.scss',
})
export class FriendCardComponent {
  @Input() name: string = 'titulo';
  @Input() type: string = 'descripcion';
  @Input() image: string = 'image';
  @Input() dateStartFriendship: string = 'startData';
  @Output() decline = new EventEmitter<void>();

  onDecline() {
    this.decline.emit();
  }
}
