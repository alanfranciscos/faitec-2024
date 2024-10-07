import { Component } from '@angular/core';
import { MainSidebarComponent } from '../../components/sidebar/main-sidebar/main-sidebar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { FriendCardComponent } from '../../components/friend-card/friend-card.component';
import { HeaderComponent } from '../../components/header/header.component';
import { DialogComponent } from '../../components/dialog/dialog.component';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';

interface CardItensType {
  name: string;
  type: string;
  dateStartFriendship: string;
  image: string;
}

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
    DialogComponent,
    PrimaryInputComponent,
  ],
  templateUrl: './friends.component.html',
  styleUrl: './friends.component.scss',
})
export class FriendsComponent {
  isAddFriendDialogOpen = false;

  toggleAddFriendDialog() {
    this.isAddFriendDialogOpen = !this.isAddFriendDialogOpen;
  }

  currentPage = 1;
  itemsPerPage = 6;
  cardItens: Array<CardItensType> = [
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
  ];
  totalPages = Math.ceil(this.cardItens.length / this.itemsPerPage); //arredonda para cima, para nao quebrar caso tenha pagina impar

  paginatedItems: Array<CardItensType> = this.calculatePaginatedItems();

  pageNumbers: number[] = this.calculatePageNumbers();

  calculatePaginatedItems(): Array<CardItensType> {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.cardItens.slice(startIndex, endIndex);
  }

  calculatePageNumbers(): number[] {
    return Array(this.totalPages)
      .fill(0)
      .map((_, i) => i + 1);
  }

  setPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.paginatedItems = this.calculatePaginatedItems();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.setPage(this.currentPage + 1);
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.setPage(this.currentPage - 1);
    }
  }
}
