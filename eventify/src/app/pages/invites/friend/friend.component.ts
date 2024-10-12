import { render } from '@testing-library/angular';
import { EventService } from './../../../services/event/event.service';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../../components/footer/footer.component';
import { HeaderComponent } from '../../../components/header/header.component';
import { NotificationCardComponent } from '../../../components/notification-card/notification-card.component';
import { MainSidebarComponent } from '../../../components/sidebar/main-sidebar/main-sidebar.component';
import { EventInvitationResponse } from '../../../domain/model/event/invitation.model';
import { FriendRequestResponse } from '../../../domain/model/event/friend_request.model';

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

  templateUrl: './friend.component.html',
  styleUrl: './friend.component.scss',
})
export class FriendComponent implements OnInit {
  constructor(private eventService: EventService) {}
  friendRequest!: FriendRequestResponse;
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
    console.log(friendRequest);
  }
}
