import { Component } from '@angular/core';
import { MainSidebarComponent } from '../../components/sidebar/main-sidebar/main-sidebar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { NotificationCardComponent } from '../../components/notification-card/notification-card.component';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../components/header/header.component';

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
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.scss',
})
export class NotificationComponent {
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
