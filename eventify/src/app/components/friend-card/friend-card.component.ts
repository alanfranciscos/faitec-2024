import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-friend-card',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './friend-card.component.html',
  styleUrl: './friend-card.component.scss',
})
export class FriendCardComponent implements OnInit {
  @Input() name: string = 'titulo';
  @Input() type: string = 'descripcion';
  @Input() image: string = '/assets/svg/logo.svg';
  @Input() dateStartFriendship: string = 'startData';
  @Output() decline = new EventEmitter<void>();

  ngOnInit(): void {
    if (!this.image) {
      this.image = '/assets/svg/logo.svg';
    }
  }

  onDecline() {
    this.decline.emit();
  }
}
