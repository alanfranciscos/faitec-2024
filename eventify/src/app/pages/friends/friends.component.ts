import { Component, OnInit } from '@angular/core';
import { MainSidebarComponent } from '../../components/sidebar/main-sidebar/main-sidebar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { FriendCardComponent } from '../../components/friend-card/friend-card.component';
import { HeaderComponent } from '../../components/header/header.component';
import { DialogComponent } from '../../components/dialog/dialog.component';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { Friend, FriendService } from '../../services/friend/friend.service';
import {
  FriendsContent,
  FriendsHeader,
} from '../../domain/model/event/friendsHeader.model';
import { ButtonComponent } from '../../components/button/button.component';
import { FormsModule } from '@angular/forms';
import { StatusRequestComponent } from '../../components/status-request/status-request.component';
import { ConfirmationDialogComponent } from '../../components/confirmation-dialog/confirmation-dialog.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-friend',
  standalone: true,
  imports: [
    MainSidebarComponent,
    FooterComponent,
    FriendCardComponent,
    CommonModule,
    HeaderComponent,
    DialogComponent,
    PrimaryInputComponent,
    ButtonComponent,
    FormsModule,
    StatusRequestComponent,
    ConfirmationDialogComponent,
  ],
  templateUrl: './friends.component.html',
  styleUrl: './friends.component.scss',
})
export class FriendsComponent implements OnInit {
  constructor(private friendService: FriendService, private router: Router) {}
  friendEmail: string = '';

  content!: FriendsHeader;
  offset = 0;
  quantityPerPage = 10;
  limit = 10;
  currentPage: number = 1;
  pages: Array<number> = [];
  friendId = 0;
  isLoading = true;

  setCurrentPageNumber(): void {
    this.currentPage = this.limit / this.offset;
    this.currentPage = !Number.isFinite(this.currentPage)
      ? 1
      : this.currentPage + 1;
  }

  private getPagesNumbers() {
    const totalPages = Math.ceil(this.content.total / this.limit);
    let i = 1;
    while (i <= totalPages) {
      this.pages.push(i);
      i++;
    }
  }
  private formatDateFromEvent(
    friends: Array<FriendsContent>
  ): Array<FriendsContent> {
    friends.map((friend) => {
      friend.dateStartFriendship = new Date(
        friend.dateStartFriendship
      ).toLocaleDateString();
    });
    return friends;
  }
  async ngOnInit(): Promise<void> {
    this.content = await this.friendService.listFriends(
      this.offset,
      this.limit
    );
    this.content.friends = this.formatDateFromEvent(this.content.friends);

    this.getPagesNumbers();
    this.setCurrentPageNumber();
    this.isLoading = false;
  }
  async goToPage(page: number): Promise<void> {
    this.isLoading = true;
    this.offset = page * this.limit - this.limit;
    this.content = await this.friendService.listFriends(
      this.offset,
      this.limit
    );
    this.content.friends = this.formatDateFromEvent(this.content.friends);
    this.setCurrentPageNumber();

    this.isLoading = false;
  }
  async nextPage(): Promise<void> {
    this.isLoading = true;
    if (this.currentPage === this.pages.length) {
      return;
    }
    this.offset = (this.currentPage + 1) * this.limit - this.limit;
    this.content = await this.friendService.listFriends(
      this.offset,
      this.limit
    );
    this.content.friends = this.formatDateFromEvent(this.content.friends);
    this.setCurrentPageNumber();
    this.isLoading = false;
  }
  async previousPage(): Promise<void> {
    this.isLoading = true;
    if (this.currentPage === 1) {
      return;
    }
    this.offset = (this.currentPage - 1) * this.limit - this.limit;
    this.content = await this.friendService.listFriends(
      this.offset,
      this.limit
    );
    this.content.friends = this.formatDateFromEvent(this.content.friends);
    this.setCurrentPageNumber();
    this.isLoading = false;
  }

  isAddFriendDialogOpen = false;

  toggleAddFriendDialog() {
    this.isAddFriendDialogOpen = !this.isAddFriendDialogOpen;
  }

  async onAddFriend() {
    const friendData: Friend = {
      email: this.friendEmail,
    };
    const response = await this.friendService.inviteFriend(friendData);
  }

  async onDeleteFriend() {
    await this.friendService.deleteFriend(this.friendId);
    window.location.reload();
  }
  showDialog = false;

  openDialog(id: number) {
    this.friendId = id;
    this.showDialog = true;
  }

  async handleConfirm(id: number) {
    this.showDialog = false;
    this.onDeleteFriend();
    this.router.navigate(['/friends']);
  }

  handleCancel() {
    this.showDialog = false;
  }
}
