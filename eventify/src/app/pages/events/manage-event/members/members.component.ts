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
import { EventParticipantsResponse } from '../../../../domain/model/event/eventparticipants.model';

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

  async ngOnInit(): Promise<void> {
    const url = this.router.url;
    let eventId = url.split('/')[2];
    eventId = eventId == null ? '-1' : eventId;
    const participants = await this.eventService.listParticipants(eventId);
    this.participants = participants;
    console.log(participants);
    this.participants = {
      ...participants,
      participants: participants.participants.map((participants) => ({
        ...participants,
        aceptedAt: new Date(participants.aceptedAt).toLocaleDateString(),
      })),
    };
  }
  isAddExpenseDialogOpen = false;
  toggleAddMemberDialog() {
    this.isAddExpenseDialogOpen = !this.isAddExpenseDialogOpen;
  }

  currentPage = 1;
  itemsPerPage = 6;

  nextPage() {
    // if (this.currentPage < this.totalPages) {
    //   this.currentPage++;
    // }
  }

  prevPage() {
    // if (this.currentPage > 1) {
    //   this.currentPage--;
    // }
  }

  goToPage(page: number) {
    // if (page >= 1 && page <= this.totalPages) {
    //   this.currentPage = page;
    // }
  }
}
