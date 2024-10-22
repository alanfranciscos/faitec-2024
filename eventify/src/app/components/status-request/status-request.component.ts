import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-status-request',
  standalone: true,
  imports: [],
  templateUrl: './status-request.component.html',
  styleUrl: './status-request.component.scss',
})
export class StatusRequestComponent {
  @Input() isLoading: boolean = false;
  @Input() isEmpty: boolean = false;
}
