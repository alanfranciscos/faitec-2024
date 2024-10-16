import { Component, OnInit } from '@angular/core';
import { MainSidebarComponent } from '../../components/sidebar/main-sidebar/main-sidebar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { FriendCardComponent } from '../../components/friend-card/friend-card.component';
import { HeaderComponent } from '../../components/header/header.component';
import { DialogComponent } from '../../components/dialog/dialog.component';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { FriendService } from '../../services/friend/friend.service';
import {
  FriendsContent,
  FriendsHeader,
} from '../../domain/model/event/friendsHeader.model';

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
  ],
  templateUrl: './friends.component.html',
  styleUrl: './friends.component.scss',
})
export class FriendsComponent implements OnInit {
  constructor(private friendService: FriendService) {}
  content!: FriendsHeader;
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
  }
  async goToPage(page: number): Promise<void> {
    this.offset = page * this.limit - this.limit;
    this.content = await this.friendService.listFriends(
      this.offset,
      this.limit
    );
    this.content.friends = this.formatDateFromEvent(this.content.friends);
    this.setCurrentPageNumber();
  }
  async nextPage(): Promise<void> {
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
  }
  async previousPage(): Promise<void> {
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
  }

  isAddFriendDialogOpen = false;

  toggleAddFriendDialog() {
    this.isAddFriendDialogOpen = !this.isAddFriendDialogOpen;
  }
}
