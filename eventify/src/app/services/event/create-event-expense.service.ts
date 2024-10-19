import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import { Expense } from '../../domain/model/event/expense.model';

export interface ExpenseInput {
  meetup_id: string;
  cost: string;
  about: string;
}

@Injectable({
  providedIn: 'root',
})
export class CreateEventExpenseService {
  constructor(private apiService: ApiService) {}
  api = this.apiService.getApi();

  async createExpenseEvent(data: ExpenseInput): Promise<String> {
    const formData = new FormData();
    formData.append('meetup_id', data.meetup_id);
    formData.append('cost', data.cost);
    formData.append('about', data.about);

    const response = await this.api.post(`/api/v1/expense`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    if (response.status != 201) {
      throw new Error('Failed to create event expense');
    }

    return response.data;
  }
}
