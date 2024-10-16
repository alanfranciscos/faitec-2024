import { EventService } from './../../../services/event/event.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../../components/footer/footer.component';
import { HeaderComponent } from '../../../components/header/header.component';
import { NotificationCardComponent } from '../../../components/notification-card/notification-card.component';
import { MainSidebarComponent } from '../../../components/sidebar/main-sidebar/main-sidebar.component';
import {
  EventInvitation,
  EventInvitationResponse,
} from '../../../domain/model/event/invitation.model';

interface CardItensType {
  title: string;
  description: string;
  dateNotification: string;
  image: string;
}
@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [
    MainSidebarComponent,
    FooterComponent,
    NotificationCardComponent,
    CommonModule,
    HeaderComponent,
  ],
  templateUrl: './event.component.html',
  styleUrl: './event.component.scss',
})
export class EventComponent implements OnInit {
  constructor(private eventService: EventService) {}
  eventInvite!: EventInvitationResponse;

  offset = 0;
  quantityPerPage = 10;
  limit = 10;
  currentPage: number = 1;

  pages: Array<number> = [];
  setCurrentPageNumber(): void {
    this.currentPage = this.limit / this.offset;
    this.currentPage = !Number.isFinite(this.currentPage)
      ? 1
      : this.currentPage + 1;
  }

  private getPagesNumbers() {
    const totalPages = Math.ceil(this.eventInvite.totalInvites / this.limit);

    let i = 1;
    while (i <= totalPages) {
      this.pages.push(i);
      i++;
    }
  }

  async ngOnInit(): Promise<void> {
    const eventInvite = await this.eventService.getEventInvitation();
    this.eventInvite = eventInvite;
    this.eventInvite = {
      ...eventInvite,
      invite: eventInvite.invite.map((element) => ({
        ...element,
        sendedAt: new Date(element.sendedAt).toLocaleDateString(),
      })),
    };

    this.getPagesNumbers();
    this.setCurrentPageNumber();
  }
  private formatDateFromEvent(
    eventInvite: Array<EventInvitation>
  ): Array<EventInvitation> {
    eventInvite.map((eventInvite) => {
      eventInvite.sendedAt = new Date(
        eventInvite.sendedAt
      ).toLocaleDateString();
    });
    return eventInvite;
  }
  async goToPage(page: number): Promise<void> {
    this.offset = page * this.limit - this.limit;
    this.eventInvite = await this.eventService.getEventInvitation();
    this.eventInvite.invite = this.formatDateFromEvent(this.eventInvite.invite);
    this.setCurrentPageNumber();
  }

  async nextPage(): Promise<void> {
    if (this.currentPage === this.pages.length) {
      return;
    }
    this.offset = (this.currentPage + 1) * this.limit - this.limit;
    this.eventInvite = await this.eventService.getEventInvitation();
    this.eventInvite.invite = this.formatDateFromEvent(this.eventInvite.invite);
    this.setCurrentPageNumber();
  }

  async previousPage(): Promise<void> {
    if (this.currentPage === 1) {
      return;
    }
    this.offset = (this.currentPage - 1) * this.limit - this.limit;
    this.eventInvite = await this.eventService.getEventInvitation();
    this.eventInvite.invite = this.formatDateFromEvent(this.eventInvite.invite);
    this.setCurrentPageNumber();
  }
}
