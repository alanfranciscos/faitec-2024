import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

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
  imports: [CommonModule],
  standalone: true,
})
export class ExpensesComponent implements OnInit {
  expenses = [
    {
      name: 'Example Name 1',
      date: '01/01/2024',
      description: 'Example Description 1',
      amount: 100.0,
    },
    {
      name: 'Example Name 2',
      date: '02/01/2024',
      description: 'Example Description 2',
      amount: 200.0,
    },
    {
      name: 'Example Name 3',
      date: '03/01/2024',
      description: 'Example Description 3',
      amount: 300.0,
    },
    {
      name: 'Example Name 4',
      date: '04/01/2024',
      description: 'Example Description 4',
      amount: 400.0,
    },
    {
      name: 'Example Name 5',
      date: '05/01/2024',
      description: 'Example Description 5',
      amount: 500.0,
    },
    {
      name: 'Example Name 6',
      date: '06/01/2024',
      description: 'Example Description 6',
      amount: 600.0,
    },
    {
      name: 'Example Name 7',
      date: '07/01/2024',
      description: 'Example Description 7',
      amount: 700.0,
    },
    {
      name: 'Example Name 8',
      date: '08/01/2024',
      description: 'Example Description 8',
      amount: 800.0,
    },
    {
      name: 'Example Name 9',
      date: '09/01/2024',
      description: 'Example Description 9',
      amount: 900.0,
    },
    {
      name: 'Example Name 10',
      date: '10/01/2024',
      description: 'Example Description 10',
      amount: 1000.0,
    },
    {
      name: 'Example Name 11',
      date: '11/01/2024',
      description: 'Example Description 11',
      amount: 1100.0,
    },
    // Add more items here if needed
  ];

  paginatedExpenses: Expense[] = [];
  currentPage = 1;
  pageSize = 10;
  totalPages = 0;
  totalExpenses = 0;
  ngOnInit() {
    this.totalPages = Math.ceil(this.expenses.length / this.pageSize);
    this.updatePagination();
    this.calculateTotalExpenses(); // Calcular a soma total ao iniciar o componente
  }

  updatePagination() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.paginatedExpenses = this.expenses.slice(startIndex, endIndex);
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updatePagination();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updatePagination();
    }
  }
  calculateTotalExpenses() {
    this.totalExpenses = this.expenses.reduce((accumulator, expense) => {
      return accumulator + expense.amount;
    }, 0);
  }
}
