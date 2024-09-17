import { Component, Input, OnInit } from '@angular/core';
import { ItemComponent } from '../item/item.component';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { SideBarItensType } from './types';
@Component({
  selector: 'app-event-layout',
  standalone: true,
  imports: [CommonModule, ItemComponent, RouterLink],
  templateUrl: './event-sidebar.component.html',
  styleUrl: './event-sidebar.component.scss',
})
export class EventLayoutComponent implements OnInit {
  constructor(private router: Router) {}
  @Input() eventName: string = 'Event Name';
  @Input() location: string = 'Local';
  @Input() date: string = '10/10/2010';
  @Input() creatorUser: string = 'John Doe';
  @Input() description: string = 'Palestras sobre inovações tecnológicas.';
  isCollapsed = false;
  sidebarItens: Array<SideBarItensType> = [
    {
      title: 'General view',
      routerLink: '/manage-event/general-view',
      isSelected: false,
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

  ngOnInit(): void {
    const activeRoute = this.router.url;
    this.sidebarItens.forEach((item) => {
      item.isSelected = item.routerLink === activeRoute;
    });
  }

  ToogleSelected(event: Event, item: SideBarItensType) {
    event.preventDefault();
    this.sidebarItens.forEach((element) => {
      element.isSelected = false;
    });
    item.isSelected = true;
  }

  toggleSidebar() {
    this.isCollapsed = !this.isCollapsed;
  }
  logout() {
    localStorage.clear();
    this.router.navigate(['/account/login']); // Redireciona para a tela de login
  }
}
