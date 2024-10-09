import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SideBarItensType } from '../main-sidebar/types';

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
  @Input() children?: Array<SideBarItensType> | null;
  @Input() isOpen: boolean = false;

  @Output() selectedChildRoute = new EventEmitter<string>();

  constructor() {}

  clickItem(route: string): void {
    this.selectedChildRoute.emit(route);
  }
}
