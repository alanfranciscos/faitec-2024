import { TotalExpenses } from './../../../../domain/model/event/totalexpenses.model.d';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { PaymentApproach } from '../../../../domain/model/event/paymentApproach.model';
import { EventService } from '../../../../services/event/event.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import {
  ExpansesResponse,
  Expense,
} from '../../../../domain/model/event/expense.model';
import { DialogComponent } from '../../../../components/dialog/dialog.component';
import { PrimaryInputComponent } from '../../../../components/primary-input/primary-input.component';

@Component({
  selector: 'app-expenses',
  templateUrl: './expenses.component.html',
  styleUrls: ['./expenses.component.scss'],
  imports: [CommonModule, DialogComponent, PrimaryInputComponent],
  standalone: true,
})
export class ExpensesComponent implements OnInit {
  constructor(private router: Router, private eventService: EventService) {}
  paymentApproach!: PaymentApproach;
  totalExpenses!: TotalExpenses;
  eventExpanses!: ExpansesResponse;

  offset = 0;
  quantityPerPage = 1;
  limit = 1;
  currentPage: number = 1;

  pages: Array<number> = [];
  setCurrentPageNumber(): void {
    this.currentPage = this.limit / this.offset;
    this.currentPage = !Number.isFinite(this.currentPage)
      ? 1
      : this.currentPage + 1;
  }

  private getPagesNumbers() {
    const totalPages = Math.ceil(this.eventExpanses.total / this.limit);
    let i = 1;
    while (i <= totalPages) {
      this.pages.push(i);
      i++;
    }
  }

  isAddExpenseDialogOpen = false;
  toggleAddExpenseDialog() {
    this.isAddExpenseDialogOpen = !this.isAddExpenseDialogOpen;
  }
  eventId = '';
  async ngOnInit() {
    const url = this.router.url;
    let eventId = url.split('/')[2];
    eventId = eventId == null ? '-1' : eventId;

    const totalExpenses = await this.eventService.getTotalExpenses(eventId);
    this.totalExpenses = totalExpenses;
    const paymentApproach = await this.eventService.getPaymentApproach(eventId);
    this.paymentApproach = paymentApproach;
    const eventExpanses = await this.eventService.getEventExpanses(
      eventId,
      this.offset,
      this.limit
    );
    this.eventExpanses = eventExpanses;
    this.eventExpanses = {
      ...eventExpanses,
      expanses: eventExpanses.expanses.map((expense) => ({
        ...expense,
        date: new Date(expense.date).toLocaleDateString(),
      })),
    };

    this.getPagesNumbers();
    this.setCurrentPageNumber();
  }
  private formatDateFromEvent(expenses: Array<Expense>): Array<Expense> {
    expenses.map((expense) => {
      expense.date = new Date(expense.date).toLocaleDateString();
    });
    return expenses;
  }
  async goToPage(page: number): Promise<void> {
    this.offset = page * this.limit - this.limit;
    this.eventExpanses = await this.eventService.getEventExpanses(
      this.eventId,
      this.offset,
      this.limit
    );
    this.eventExpanses.expanses = this.formatDateFromEvent(
      this.eventExpanses.expanses
    );
    this.setCurrentPageNumber();
  }

  async nextPage(): Promise<void> {
    if (this.currentPage === this.pages.length) {
      return;
    }
    this.offset = (this.currentPage + 1) * this.limit - this.limit;
    this.eventExpanses = await this.eventService.getEventExpanses(
      this.eventId,
      this.offset,
      this.limit
    );
    this.eventExpanses.expanses = this.formatDateFromEvent(
      this.eventExpanses.expanses
    );
    this.setCurrentPageNumber();
  }

  async previousPage(): Promise<void> {
    if (this.currentPage === 1) {
      return;
    }
    this.offset = (this.currentPage - 1) * this.limit - this.limit;
    this.eventExpanses = await this.eventService.getEventExpanses(
      this.eventId,
      this.offset,
      this.limit
    );
    this.eventExpanses.expanses = this.formatDateFromEvent(
      this.eventExpanses.expanses
    );
    this.setCurrentPageNumber();
  }
}
