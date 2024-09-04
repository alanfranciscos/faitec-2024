import { Component, Input } from '@angular/core';
import { ItemComponent } from '../item/item.component';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SideBarItensType } from './types';
@Component({
  selector: 'app-event-layout',
  standalone: true,
  imports: [CommonModule, ItemComponent, RouterLink],
  templateUrl: './event-sidebar.component.html',
  styleUrl: './event-sidebar.component.scss',
})
export class EventLayoutComponent {
  @Input() eventName: string = 'Event Name';
  @Input() location: string = 'Local';
  @Input() date: string = '10/10/2010';
  @Input() creatorUser: string = 'John Doe';

  isCollapsed = false;
  sidebarItens: Array<SideBarItensType> = [
    {
      title: 'General view',
      routerLink: '/manage-event/general-view',
      isSelected: true,
      icon: 'far fa-map',
    },
    {
      title: 'Expenses',
      isSelected: false,
      routerLink: '/manage-event/expenses',
      icon: 'fas fa-dollar-sign',
    },
    {
      title: 'Members',
      isSelected: false,
      routerLink: '/manage-event/members',
      icon: 'fas fa-user-friends',
    },
  ];

  ToogleSelected(event: Event, item: SideBarItensType) {
    event.preventDefault();
    this.sidebarItens.forEach((element) => {
      element.isSelected = false;
      if (element.title === item.title) {
        element.isSelected = true;
      }
    });
  }

  toggleSidebar() {
    this.isCollapsed = !this.isCollapsed;
  }
}
