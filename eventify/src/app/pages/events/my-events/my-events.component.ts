import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ButtonComponent } from '../../../components/button/button.component';
import { CardComponent } from '../../../components/card/card.component';
import { HeaderComponent } from '../../../components/header/header.component';
import {
  EventContent,
  EventHeader,
} from '../../../domain/model/event/eventHeader.model';
import { EventService } from '../../../services/event/event.service';
import { StatusRequestComponent } from '../../../components/status-request/status-request.component';

@Component({
  selector: 'app-my-events',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    ButtonComponent,
    CardComponent,
    HeaderComponent,
    StatusRequestComponent,
    ButtonComponent,
  ],
  templateUrl: './my-events.component.html',
  styleUrls: ['./my-events.component.scss'],
})
export class MyEventsComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private eventService: EventService
  ) {}
  content: EventHeader = {
    events: [],
    total: 0,
  };

  offset = 0;
  quantityPerPage = 6;
  limit = 6;
  currentPage: number = 1;
  pages: Array<number> = [];

  isloading = true;

  setCurrentPageNumber(): void {
    this.currentPage = this.limit / this.offset;
    this.currentPage = !Number.isFinite(this.currentPage)
      ? 1
      : this.currentPage + 1;
  }

  private formatDateFromEvent(
    events: Array<EventContent>
  ): Array<EventContent> {
    events.map((event) => {
      event.dateStart = new Date(event.dateStart).toLocaleDateString();
      event.dateEnd = new Date(event.dateEnd).toLocaleDateString();
    });
    return events;
  }

  private getPagesNumbers() {
    const totalPages = Math.ceil(this.content.total / this.limit);
    let i = 1;
    while (i <= totalPages) {
      this.pages.push(i);
      i++;
    }
  }

  async ngOnInit(): Promise<void> {
    this.content = await this.eventService.listEvents(this.offset, this.limit);
    this.content.events = this.formatDateFromEvent(this.content.events);
    this.setCurrentPageNumber();
    this.getPagesNumbers();
    this.isloading = false;
  }

  async goToPage(page: number): Promise<void> {
    this.isloading = true;
    this.offset = page * this.limit - this.limit;
    this.content = await this.eventService.listEvents(this.offset, this.limit);
    this.content.events = this.formatDateFromEvent(this.content.events);
    this.setCurrentPageNumber();
    this.isloading = false;
  }

  async nextPage(): Promise<void> {
    this.isloading = true;
    if (this.currentPage === this.pages.length) {
      return;
    }
    this.offset = (this.currentPage + 1) * this.limit - this.limit;
    this.content = await this.eventService.listEvents(this.offset, this.limit);
    this.content.events = this.formatDateFromEvent(this.content.events);
    this.setCurrentPageNumber();
    this.isloading = false;
  }

  async previousPage(): Promise<void> {
    this.isloading = true;
    if (this.currentPage === 1) {
      return;
    }
    this.offset = (this.currentPage - 1) * this.limit - this.limit;
    this.content = await this.eventService.listEvents(this.offset, this.limit);
    this.content.events = this.formatDateFromEvent(this.content.events);
    this.setCurrentPageNumber();
    this.isloading = false;
  }
}
