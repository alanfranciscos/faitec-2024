import { Component } from '@angular/core';
import { MainLayoutComponent } from '../../../../layout/main-layout/main-layout.component';
import { FooterComponent } from '../../../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../../../components/header/header.component';
import { MemberCardComponent } from '../../../../components/member-card/member-card.component';

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
    MainLayoutComponent,
    FooterComponent,
    CommonModule,
    HeaderComponent,
    MemberCardComponent,
  ],
  templateUrl: './members.component.html',
  styleUrl: './members.component.scss',
})
export class MembersComponent {
  currentPage = 1;
  itemsPerPage = 6;
  cardItens: Array<CardItensType> = [
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/avatar.svg',
    },
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/avatar.svg',
    },
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/avatar.svg',
    },
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/avatar.svg',
    },
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/avatar.svg',
    },
    {
      name: 'Fulano da silva',
      type: 'Participante',
      dateStartFriendship: '2024-09-01',
      image: '/assets/svg/avatar.svg',
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
