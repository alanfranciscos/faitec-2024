import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-friend-card',
  standalone: true,
  imports: [],
  templateUrl: './friend-card.component.html',
  styleUrl: './friend-card.component.scss',
})
export class FriendCardComponent {
  @Input() name: string = 'titulo';
  @Input() type: string = 'descripcion';
  @Input() image: string = 'image';
  @Input() dateStartFriendship: string = 'startData';
}
