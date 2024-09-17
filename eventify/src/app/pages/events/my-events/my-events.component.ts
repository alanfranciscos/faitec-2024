import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ButtonComponent } from '../../../components/button/button.component';
import { CardComponent } from '../../../components/card/card.component';
import { HeaderComponent } from '../../../components/header/header.component';

interface CardItensType {
  title: string;
  description: string;
  startData: string;
  endData: string;
  image: string;
  status: string;
}

@Component({
  selector: 'app-my-events',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    ButtonComponent,
    CardComponent,
    HeaderComponent,
  ],
  templateUrl: './my-events.component.html',
  styleUrls: ['./my-events.component.scss'],
})
export class MyEventsComponent {
  currentPage = 1;
  itemsPerPage = 6;
  cardItens: Array<CardItensType> = [
    {
      title: 'Tech Conference 2024',
      description: 'Palestras sobre inovações tecnológicas.',
      startData: '2024-09-01',
      endData: '2024-09-03',
      image: '/assets/svg/logo.svg',
      status: 'confirmado',
    },
    {
      title: 'Workshop de IA',
      description: 'Aprenda sobre inteligência artificial aplicada.',
      startData: '2024-10-15',
      endData: '2024-10-16',
      image: '/assets/svg/logo.svg',
      status: 'confirmado',
    },
    {
      title: 'Reunião de Projetos',
      description: 'Discussão sobre o andamento dos projetos atuais.',
      startData: '2024-08-20',
      endData: '2024-08-20',
      image: '/assets/svg/logo.svg',
      status: 'cancelado',
    },
    {
      title: 'Hackathon',
      description: 'Competição de desenvolvimento de software.',
      startData: '2024-11-05',
      endData: '2024-11-07',
      image: '/assets/svg/logo.svg',
      status: 'confirmado',
    },
    {
      title: 'Seminário de Segurança',
      description: 'Debate sobre segurança digital e cibersegurança.',
      startData: '2024-12-01',
      endData: '2024-12-02',
      image: '/assets/svg/logo.svg',
      status: 'pendente',
    },
    {
      title: 'Feira de Startups',
      description: 'Exposição das novas startups da região.',
      startData: '2024-09-25',
      endData: '2024-09-26',
      image: '/assets/svg/logo.svg',
      status: 'confirmado',
    },
    {
      title: 'Encontro de Desenvolvedores',
      description: 'Networking e troca de experiências entre devs.',
      startData: '2024-10-10',
      endData: '2024-10-11',
      image: '/assets/svg/logo.svg',
      status: 'rolando',
    },
    {
      title: 'Webinar sobre DevOps',
      description: 'Sessão online sobre práticas de DevOps.',
      startData: '2024-08-28',
      endData: '2024-08-28',
      image: '/assets/svg/logo.svg',
      status: 'confirmado',
    },
    {
      title: 'Lançamento de Produto',
      description: 'Apresentação de novo software no mercado.',
      startData: '2024-09-30',
      endData: '2024-09-30',
      image: '/assets/svg/logo.svg',
      status: 'pendente',
    },
    {
      title: 'Palestra sobre Cloud',
      description: 'Exploração das novas tendências em cloud computing.',
      startData: '2024-11-12',
      endData: '2024-11-12',
      image: '/assets/svg/logo.svg',
      status: 'confirmado',
    },
    {
      title: 'Oficina de UX/UI',
      description: 'Treinamento prático em design de interfaces.',
      startData: '2024-08-25',
      endData: '2024-08-25',
      image: '/assets/svg/logo.svg',
      status: 'rolando',
    },
    {
      title: 'Curso de Python',
      description: 'Curso intensivo sobre programação em Python.',
      startData: '2024-09-10',
      endData: '2024-09-12',
      image: '/assets/svg/logo.svg',
      status: 'pendente',
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
