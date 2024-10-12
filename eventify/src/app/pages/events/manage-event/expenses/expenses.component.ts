import { TotalExpenses } from './../../../../domain/model/event/totalexpenses.model.d';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { PaymentApproach } from '../../../../domain/model/event/paymentApproach.model';
import { EventService } from '../../../../services/event/event.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ExpansesResponse } from '../../../../domain/model/event/expense.model';
import { DialogComponent } from '../../../../components/dialog/dialog.component';
import { PrimaryInputComponent } from '../../../../components/primary-input/primary-input.component';

interface Expense {
  name: string;
  date: string;
  description: string;
  amount: number;
}

@Component({
  selector: 'app-expenses',
  templateUrl: './expenses.component.html',
  styleUrls: ['./expenses.component.scss'],
  imports: [CommonModule, DialogComponent, PrimaryInputComponent],
  standalone: true,
})
export class ExpensesComponent implements OnInit {
  paymentApproach!: PaymentApproach;
  totalExpenses!: TotalExpenses;
  eventExpanses!: ExpansesResponse;
  constructor(private router: Router, private eventService: EventService) {}

  currentPage = 1;
  pageSize = 10;
  totalPages = 0;
  isAddExpenseDialogOpen = false;
  toggleAddExpenseDialog() {
    this.isAddExpenseDialogOpen = !this.isAddExpenseDialogOpen;
  }
  async ngOnInit() {
    const url = this.router.url;
    let eventId = url.split('/')[2];
    eventId = eventId == null ? '-1' : eventId;
    const totalExpenses = await this.eventService.getTotalExpenses(eventId);
    this.totalExpenses = totalExpenses;
    const paymentApproach = await this.eventService.getPaymentApproach(eventId);
    this.paymentApproach = paymentApproach;
    const eventExpanses = await this.eventService.getEventExpanses(eventId);
    this.eventExpanses = eventExpanses;
    this.eventExpanses = {
      ...eventExpanses,
      expanses: eventExpanses.expanses.map((expense) => ({
        ...expense,
        date: new Date(expense.date).toLocaleDateString(),
      })),
    };

    this.totalPages = 0;
    this.updatePagination();
  }

  updatePagination() {
    // const startIndex = (this.currentPage - 1) * this.pageSize;
    // const endIndex = startIndex + this.pageSize;
    // this.paginatedExpenses = this.expenses.slice(startIndex, endIndex);
  }

  previousPage() {
    // if (this.currentPage > 1) {
    //   this.currentPage--;
    //   this.updatePagination();
    // }
  }

  nextPage() {
    //   if (this.currentPage < this.totalPages) {
    //     this.currentPage++;
    //     this.updatePagination();
    //   }
  }
}
