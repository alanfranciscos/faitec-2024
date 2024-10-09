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
      children: null,
    },
    {
      title: 'Friends',
      isSelected: false,
      routerLink: '/friends',
      icon: 'fas fa-user-friends',
      children: null,
    },
    {
      title: 'Invite',
      isSelected: false,
      routerLink: '/invite/friend',
      icon: 'fas fa-bell',
      children: [
        {
          title: 'Friends',
          isSelected: false,
          routerLink: '/invite/friend',
          icon: 'fas fa-user-friends',
          children: null,
        },
        {
          title: 'Event',
          isSelected: false,
          routerLink: '/invite/event',
          icon: 'fas fa-calendar',
          children: null,
        },
      ],
    },
  ];

  ngOnInit(): void {
    // const activeRoute = this.router.url;
    this.sidebarItens.forEach((item) => {
      item.isSelected = item.routerLink === activeRoute;
    });
  }

  ToogleSelected(event: Event, item: SideBarItensType) {
    console.log('aqui');
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

  changeLinkChild(item: string) {
    const activeRoute = this.router.url;
    if (item) {
      this.router.navigate([item]);
    }

    this.sidebarItens.forEach((element) => {
      if (element.children) {
        for (const child of element.children) {
          console.log(activeRoute);
          if (activeRoute == child.routerLink) {
            child.isSelected = true;
          } else {
            child.isSelected = false;
          }
        }
      }
    });
  }
}

// import { Component, EventEmitter, OnInit, Output } from '@angular/core';
// import { ItemComponent } from '../item/item.component';
// import { SideBarItensType } from './types';
// import { CommonModule } from '@angular/common';
// import { Router, RouterLink } from '@angular/router';
// import { AuthenticationLayoutComponent } from '../../../layout/authentication-layout/authentication-layout.component';

// @Component({
//   selector: 'app-main-layout',
//   standalone: true,
//   imports: [
//     CommonModule,
//     ItemComponent,
//     RouterLink,
//     AuthenticationLayoutComponent,
//   ],
//   templateUrl: './main-sidebar.component.html',
//   styleUrl: './main-sidebar.component.scss',
// })
// export class MainSidebarComponent implements OnInit {
//   constructor(private router: Router) {}

//   logout() {
//     localStorage.clear();
//     this.router.navigate(['/account/login']); // Redireciona para a tela de login
//   }
//   @Output() sidebarToggle = new EventEmitter<void>();
//   isCollapsed = false;
//   isDropdownOpen = false;
//   sidebarItens: Array<SideBarItensType> = [
//     {
//       title: 'My events',
//       routerLink: '/',
//       isSelected: false,
//       icon: 'fas fa-calendar',
//     },
//     {
//       title: 'Friends',
//       isSelected: false,
//       routerLink: '/friends',
//       icon: 'fas fa-user-friends',
//     },

//     {
//       title: 'ivite',
//       isSelected: false,
//       routerLink: '/invite/friend',
//       icon: 'fas fa-bell',
//     },

//     {
//       title: 'Friend',
//       isSelected: false,
//       routerLink: '/invite/friend',
//       icon: 'fas fa-bell',
//     },
//     {
//       title: 'Event',
//       isSelected: false,
//       routerLink: '/invite/event',
//       icon: 'fas fa-bell',
//     },
//   ];

//   private invites = ['/invite/friend', '/invite/event'];

//   ngOnInit(): void {
//     const activeRoute = this.router.url;

//     for (const invite of this.invites) {
//       if (activeRoute == invite) {
//         this.isDropdownOpen = true;
//       }
//     }
//   }

//   ToogleSelected(event: Event, item: SideBarItensType) {
//     event.preventDefault();
//     this.sidebarItens.forEach((element) => {
//       element.isSelected = false;
//     });
//     item.isSelected = true;
//   }

//   toggleSidebar() {
//     this.isCollapsed = !this.isCollapsed;
//     this.sidebarToggle.emit();
//   }
//   toggleDropdown() {
//     this.isDropdownOpen = !this.isDropdownOpen;
//   }

//   isRouteActive(route: string): boolean {
//     let isDropdown = false;
//     for (const invite of this.invites) {
//       if (route == invite) {
//         isDropdown = true;
//       }
//     }

//     console.log(route);

//     if (!isDropdown) {
//       this.isDropdownOpen = false;
//     }

//     return this.router.url === route;
//   }
// }
