import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ItemComponent } from '../item/item.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { SideBarItensType } from './types';
import { EventService } from '../../../services/event/event.service';

@Component({
  selector: 'app-event-layout',
  standalone: true,
  imports: [CommonModule, ItemComponent, RouterLink],
  templateUrl: './event-sidebar.component.html',
  styleUrl: './event-sidebar.component.scss',
})
export class EventLayoutComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private eventService: EventService
  ) {}
  eventName!: string;
  location!: string;
  description!: string;
  eventImage!: string;
  isCollapsed = false;
  @Output() sidebarToggle = new EventEmitter<void>();

  sidebarItens: Array<SideBarItensType> = [];

  async ngOnInit(): Promise<void> {
    const eventId = this.activatedRoute.snapshot.paramMap.get('id');
    const eventData = await this.eventService.getEventData(Number(eventId));
    this.eventName = eventData.eventname;
    this.location = eventData.local_name;
    this.description = eventData.eventdescription;
    this.eventImage = await this.eventService.getEventDataImage(
      Number(eventId)
    );

    this.sidebarItens = [
      {
        title: 'General view',
        routerLink: '/event/' + eventId,
        isSelected: false,
        // icon: 'far fa-map',
        icon: 'fad fa-route',
      },
      {
        title: 'Expenses',
        isSelected: false,
        routerLink: `/event/${eventId}/expenses`,
        // icon: 'fas fa-dollar-sign',
        icon: 'fad fa-money-bill-wave',
      },
      {
        title: 'Members',
        isSelected: false,
        routerLink: `/event/${eventId}/members`,
        // icon: 'fas fa-user-friends',
        icon: 'fad fa-users',
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
