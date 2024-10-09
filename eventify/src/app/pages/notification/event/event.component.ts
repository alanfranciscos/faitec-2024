import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainSidebarComponent } from '../../../components/sidebar/main-sidebar/main-sidebar.component';
import { FooterComponent } from '../../../components/footer/footer.component';
import { NotificationCardComponent } from '../../../components/notification-card/notification-card.component';
import { HeaderComponent } from '../../../components/header/header.component';

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
export class EventComponent {
  currentPage = 1;
  itemsPerPage = 6;
  cardItens: Array<CardItensType> = [
    {
      title: 'Tech Conference 2024',
      description: 'Palestras sobre inovações tecnológicas.',
      dateNotification: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      title: 'Tech Conference 2024',
      description: 'Palestras sobre inovações tecnológicas.',
      dateNotification: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      title: 'Tech Conference 2024',
      description: 'Palestras sobre inovações tecnológicas.',
      dateNotification: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      title: 'Tech Conference 2024',
      description: 'Palestras sobre inovações tecnológicas.',
      dateNotification: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      title: 'Tech Conference 2024',
      description: 'Palestras sobre inovações tecnológicas.',
      dateNotification: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      title: 'Tech Conference 2024',
      description: 'Palestras sobre inovações tecnológicas.',
      dateNotification: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      title: 'Tech Conference 2024',
      description: 'Palestras sobre inovações tecnológicas.',
      dateNotification: '2024-09-01',
      image: '/assets/svg/logo.svg',
    },
    {
      title: 'Tech Conference 2024',
      description: 'Palestras sobre inovações tecnológicas.',
      dateNotification: '2024-09-01',
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
