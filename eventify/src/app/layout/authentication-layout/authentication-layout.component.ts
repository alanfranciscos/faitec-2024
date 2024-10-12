import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-authentication-layout',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './authentication-layout.component.html',
  styleUrl: './authentication-layout.component.scss',
})
export class AuthenticationLayoutComponent {
  @Input() title: string = '';
  @Input() image: string = '/assets/svg/logo.svg';
  @Input() heightvalue?: string;
  @Input() widthvalue: string = '30.75rem';
}
