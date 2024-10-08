import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ItemComponent } from '../item/item.component';
import { SideBarItensType } from './types';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthenticationLayoutComponent } from '../../../layout/authentication-layout/authentication-layout.component';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [
    CommonModule,
    ItemComponent,
    RouterLink,
    AuthenticationLayoutComponent,
  ],
  templateUrl: './main-sidebar.component.html',
  styleUrl: './main-sidebar.component.scss',
})
export class MainSidebarComponent implements OnInit {
  constructor(private router: Router) {}

  logout() {
    localStorage.clear();
    this.router.navigate(['/account/login']); // Redireciona para a tela de login
  }
  @Output() sidebarToggle = new EventEmitter<void>();
  isCollapsed = false;
  sidebarItens: Array<SideBarItensType> = [
    {
      title: 'My events',
      routerLink: '/',
      isSelected: false,
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
    this.sidebarToggle.emit();
  }
}
