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
      title: 'Invites',
      isSelected: false,
      icon: 'fas fa-bell',
      children: [
        {
          title: 'Friends',
          routerLink: '/invite/friend',
          isSelected: false,
          icon: 'fas fa-user-friends',
        },
        {
          title: 'Events',
          routerLink: '/invite/event',
          isSelected: false,
          icon: 'fas fa-calendar',
        },
      ],
    },
  ];

  ngOnInit(): void {
    const activeRoute = this.router.url;
    this.sidebarItens.forEach((item) => {
      item.isSelected = item.routerLink === activeRoute;

      if (item.children) {
        item.children.forEach((child) => {
          child.isSelected = child.routerLink === activeRoute;
        });
      }
    });

    this.selectFather();
  }

  private unselectAll() {
    this.sidebarItens.forEach((element) => {
      element.isSelected = false;
      if (element.children) {
        element.children.forEach((child) => {
          child.isSelected = false;
        });
      }
    });
  }

  private selectFather() {
    this.sidebarItens.forEach((element) => {
      if (element.children) {
        element.children.forEach((child) => {
          if (child.isSelected) {
            element.isSelected = true;
          }
        });
      }
    });
  }

  ToogleSelected(event: Event, item: SideBarItensType) {
    event.preventDefault();

    this.unselectAll();

    item.isSelected = true;
    if (item.children) {
      item.children[0].isSelected = true;
      this.router.navigate([item.children[0].routerLink]);
    }

    this.selectFather();
  }

  toggleSidebar() {
    this.isCollapsed = !this.isCollapsed;
    this.sidebarToggle.emit();
  }
}
