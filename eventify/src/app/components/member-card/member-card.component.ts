import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-member-card',
  standalone: true,
  imports: [],
  templateUrl: './member-card.component.html',
  styleUrl: './member-card.component.scss',
})
export class MemberCardComponent {
  @Input() name: string = 'titulo';
  @Input() type: string = 'descripcion';
  @Input() image: string = 'image';
  @Input() dateStartFriendship: string = 'startData';
}
