import { Component, OnInit } from '@angular/core';
import { MainSidebarComponent } from '../../../../components/sidebar/main-sidebar/main-sidebar.component';
import { FooterComponent } from '../../../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../../../components/header/header.component';
import { MemberCardComponent } from '../../../../components/member-card/member-card.component';
import { DialogComponent } from '../../../../components/dialog/dialog.component';
import { PrimaryInputComponent } from '../../../../components/primary-input/primary-input.component';
import { EventService } from '../../../../services/event/event.service';
import { Router } from '@angular/router';
import {
  EventParticipants,
  EventParticipantsResponse,
} from '../../../../domain/model/event/eventparticipants.model';

interface CardItensType {
  name: string;
  type: string;
  dateStartFriendship: string;
  image: string;
}

@Component({
  selector: 'app-member',
  standalone: true,
  imports: [
    MainSidebarComponent,
    FooterComponent,
    CommonModule,
    HeaderComponent,
    MemberCardComponent,
    DialogComponent,
    PrimaryInputComponent,
  ],
  templateUrl: './members.component.html',
  styleUrl: './members.component.scss',
})
export class MembersComponent implements OnInit {
  constructor(private eventService: EventService, private router: Router) {}
  participants!: EventParticipantsResponse;
  offset = 0;
  quantityPerPage = 1;
  limit = 1;
  currentPage: number = 1;

  pages: Array<number> = [];
  setCurrentPageNumber(): void {
    this.currentPage = this.limit / this.offset;
    this.currentPage = !Number.isFinite(this.currentPage)
      ? 1
      : this.currentPage + 1;
  }
  private getPagesNumbers() {
    const totalPages = Math.ceil(
      this.participants.totalParticipants / this.limit
    );
    let i = 1;
    while (i <= totalPages) {
      this.pages.push(i);
      i++;
    }
  }
  eventId = '';
  async ngOnInit(): Promise<void> {
    const url = this.router.url;
    let eventId = url.split('/')[2];
    eventId = eventId == null ? '-1' : eventId;
    const participants = await this.eventService.listParticipants(
      eventId,
      this.offset,
      this.limit
    );
    this.participants = participants;
    this.participants = {
      ...participants,
      participants: participants.participants.map((participants) => ({
        ...participants,
        aceptedAt: new Date(participants.aceptedAt).toLocaleDateString(),
      })),
    };
    this.getPagesNumbers();
    this.setCurrentPageNumber();
  }
  isAddExpenseDialogOpen = false;
  toggleAddMemberDialog() {
    this.isAddExpenseDialogOpen = !this.isAddExpenseDialogOpen;
  }
  private formatDateFromEvent(
    participants: Array<EventParticipants>
  ): Array<EventParticipants> {
    participants.map((participants) => {
      participants.aceptedAt = new Date(
        participants.aceptedAt
      ).toLocaleDateString();
    });
    return participants;
  }

  async goToPage(page: number): Promise<void> {
    this.offset = page * this.limit - this.limit;
    this.participants = await this.eventService.listParticipants(
      this.eventId,
      this.offset,
      this.limit
    );
    this.participants.participants = this.formatDateFromEvent(
      this.participants.participants
    );
    this.setCurrentPageNumber();
  }

  async nextPage(): Promise<void> {
    if (this.currentPage === this.pages.length) {
      return;
    }
    this.offset = (this.currentPage + 1) * this.limit - this.limit;
    this.participants = await this.eventService.listParticipants(
      this.eventId,
      this.offset,
      this.limit
    );
    this.participants.participants = this.formatDateFromEvent(
      this.participants.participants
    );
    this.setCurrentPageNumber();
  }

  async previousPage(): Promise<void> {
    if (this.currentPage === 1) {
      return;
    }
    this.offset = (this.currentPage - 1) * this.limit - this.limit;
    this.participants = await this.eventService.listParticipants(
      this.eventId,
      this.offset,
      this.limit
    );
    this.participants.participants = this.formatDateFromEvent(
      this.participants.participants
    );
    this.setCurrentPageNumber();
  }
}
