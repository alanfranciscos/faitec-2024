import { Component } from '@angular/core';
import { MainSidebarComponent } from '../../components/sidebar/main-sidebar/main-sidebar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { FriendCardComponent } from '../../components/friend-card/friend-card.component';
import { HeaderComponent } from '../../components/header/header.component';

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
  ],
  templateUrl: './friends.component.html',
  styleUrl: './friends.component.scss',
})
export class FriendsComponent {
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
  get paginatedItems(): Array<CardItensType> {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.cardItens.slice(startIndex, endIndex);
  }

  get totalPages(): number {
    return Math.ceil(this.cardItens.length / this.itemsPerPage);
  }

  get pageNumbers(): number[] {
    const totalPages = this.totalPages;
    return Array.from({ length: totalPages }, (_, i) => i + 1);
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }
}
