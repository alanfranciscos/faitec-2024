import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-authentication-layout',
  standalone: true,
  imports: [],
  templateUrl: './authentication-layout.component.html',
  styleUrl: './authentication-layout.component.scss',
})
export class AuthenticationLayoutComponent {
  @Input() title: string = '';
}
