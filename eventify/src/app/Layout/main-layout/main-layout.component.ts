import { Component } from '@angular/core';
import { ItemComponent } from '../../components/sidebar/item/item.component';
import { SideBarItensType } from './types';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [CommonModule, ItemComponent, RouterLink],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.scss',
})
export class MainLayoutComponent {
  isCollapsed = false;
  sidebarItens: Array<SideBarItensType> = [
    {
      title: 'My events',
      routerLink: '/',
      isSelected: true,
      icon: 'fas fa-calendar',
    },
    {
      title: 'Friends',
      isSelected: false,
      routerLink: '/friends',
      icon: 'fas fa-user-friends',
    },
    {
      title: 'Notifications',
      isSelected: false,
      routerLink: '/notification',
      icon: 'fas fa-bell',
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
