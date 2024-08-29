import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-item',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './item.component.html',
  styleUrl: './item.component.scss',
})
export class ItemComponent {
  @Input() text!: string;
  @Input() icon!: string;
  @Input() routerLink!: string;
  @Input() selected: boolean = false;
  @Input() isCollapsed!: boolean;

  constructor() {}
}
