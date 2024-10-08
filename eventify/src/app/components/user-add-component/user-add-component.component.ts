import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-user-add-component',
  standalone: true,
  imports: [],
  templateUrl: './user-add-component.component.html',
  styleUrl: './user-add-component.component.scss',
})
export class UserAddComponentComponent {
  @Input() id_type: 'friend' | 'member' = 'friend';

  @Input() name: string = 'UserName';
  @Input() type: string = 'descripcion';
  @Input() image: string = 'image';
  @Input() dateStartFriendship: string = 'startData';
  @Input() dateStartInEvent: string = 'startData';
}
