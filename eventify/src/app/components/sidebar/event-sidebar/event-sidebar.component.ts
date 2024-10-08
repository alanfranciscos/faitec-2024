import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ItemComponent } from '../item/item.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { SideBarItensType } from './types';

@Component({
  selector: 'app-event-layout',
  standalone: true,
  imports: [CommonModule, ItemComponent, RouterLink],
  templateUrl: './event-sidebar.component.html',
  styleUrl: './event-sidebar.component.scss',
})
export class EventLayoutComponent implements OnInit {
  constructor(private activatedRoute: ActivatedRoute, private router: Router) {}
  @Input() eventName: string = 'Event Name';
  @Input() location: string = 'Local';
  @Input() date: string = '10/10/2010';
  @Input() creatorUser: string = 'John Doe';
  @Input() description: string = 'Palestras sobre inovações tecnológicas.';
  isCollapsed = false;
  @Output() sidebarToggle = new EventEmitter<void>();

  sidebarItens: Array<SideBarItensType> = [];

  ngOnInit(): void {
    const eventId = this.activatedRoute.snapshot.paramMap.get('id');

    this.sidebarItens = [
      {
        title: 'General view',
        routerLink: '/event/' + eventId,
        isSelected: false,
        icon: 'far fa-map',
      },
      {
        title: 'Expenses',
        isSelected: false,
        routerLink: `/event/${eventId}/expenses`,
        icon: 'fas fa-dollar-sign',
      },
      {
        title: 'Members',
        isSelected: false,
        routerLink: `/event/${eventId}/members`,
        icon: 'fas fa-user-friends',
      },
    ];

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
  home() {
    this.router.navigate(['/']);
  }
}
