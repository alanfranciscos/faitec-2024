import { Component, EventEmitter, Output } from '@angular/core';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-add-friend-dialog',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './add-friend-dialog.component.html',
  styleUrl: './add-friend-dialog.component.scss',
})
export class AddFriendDialogComponent {
  @Output() close = new EventEmitter<void>();
  closeDialog() {
    this.close.emit();
  }
}
