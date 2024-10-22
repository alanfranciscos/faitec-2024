import { render } from '@testing-library/angular';
import { EventService } from './../../../services/event/event.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../../components/footer/footer.component';
import { HeaderComponent } from '../../../components/header/header.component';
import { NotificationCardComponent } from '../../../components/notification-card/notification-card.component';
import { MainSidebarComponent } from '../../../components/sidebar/main-sidebar/main-sidebar.component';
import { EventInvitationResponse } from '../../../domain/model/event/invitation.model';
import {
  FriendRequest,
  FriendRequestResponse,
} from '../../../domain/model/event/friend_request.model';
import { FriendService } from '../../../services/friend/friend.service';
import { StatusRequestComponent } from '../../../components/status-request/status-request.component';
import { Router } from '@angular/router';
import { ConfirmationDialogComponent } from '../../../components/confirmation-dialog/confirmation-dialog.component';

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
    StatusRequestComponent,
    ConfirmationDialogComponent,
  ],

  templateUrl: './friend.component.html',
  styleUrl: './friend.component.scss',
})
export class FriendComponent implements OnInit {
  constructor(
    private eventService: EventService,
    private friendService: FriendService,
    private router: Router
  ) {}
  friendRequest!: FriendRequestResponse;

  offset = 0;
  quantityPerPage = 10;
  limit = 10;
  currentPage: number = 1;
  inviteId = 0;
  isLoading = false;

  pages: Array<number> = [];
  setCurrentPageNumber(): void {
    this.currentPage = this.limit / this.offset;
    this.currentPage = !Number.isFinite(this.currentPage)
      ? 1
      : this.currentPage + 1;
  }

  private getPagesNumbers() {
    const totalPages = Math.ceil(this.friendRequest.totalInvites / this.limit);

    let i = 1;
    while (i <= totalPages) {
      this.pages.push(i);
      i++;
    }
  }

  async ngOnInit(): Promise<void> {
    const friendRequest = await this.eventService.getFriendRequest();
    this.friendRequest = friendRequest;
    this.friendRequest = {
      ...friendRequest,
      invite: friendRequest.invite.map((element) => ({
        ...element,
        sendedAt: new Date(element.sendedAt).toLocaleDateString(),
      })),
    };
    this.getPagesNumbers();
    this.setCurrentPageNumber();
  }
  private formatDateFromEvent(
    friendRequest: Array<FriendRequest>
  ): Array<FriendRequest> {
    friendRequest.map((friendRequest) => {
      friendRequest.sendedAt = new Date(
        friendRequest.sendedAt
      ).toLocaleDateString();
    });
    return friendRequest;
  }
  async goToPage(page: number): Promise<void> {
    this.offset = page * this.limit - this.limit;
    this.friendRequest = await this.eventService.getFriendRequest();
    this.friendRequest.invite = this.formatDateFromEvent(
      this.friendRequest.invite
    );
    this.setCurrentPageNumber();
  }

  async nextPage(): Promise<void> {
    if (this.currentPage === this.pages.length) {
      return;
    }
    this.offset = (this.currentPage + 1) * this.limit - this.limit;
    this.friendRequest = await this.eventService.getFriendRequest();
    this.friendRequest.invite = this.formatDateFromEvent(
      this.friendRequest.invite
    );
    this.setCurrentPageNumber();
  }

  async previousPage(): Promise<void> {
    if (this.currentPage === 1) {
      return;
    }
    this.offset = (this.currentPage - 1) * this.limit - this.limit;
    this.friendRequest = await this.eventService.getEventInvitation();
    this.friendRequest.invite = this.formatDateFromEvent(
      this.friendRequest.invite
    );
    this.setCurrentPageNumber();
  }

  acceptFriendRequest(requestId: number) {
    this.friendService.acceptRequest(requestId);
    window.location.reload();
  }

  declineFriendRequest() {
    this.friendService.declineRequest(this.inviteId);
    window.location.reload();
  }
  showDialog = false;
  openDialog(id: number) {
    this.showDialog = true;
    this.inviteId = id;
  }

  async handleConfirm(id: number) {
    this.showDialog = false;
    this.declineFriendRequest();
    this.router.navigate(['/invite/friend']);
  }

  handleCancel() {
    this.showDialog = false;
  }
}
