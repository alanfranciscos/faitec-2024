import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';

@Injectable({
  providedIn: 'root',
})
export class DeleteEventExpenseService {
  constructor(private apiService: ApiService) {}
  api = this.apiService.getApi();

  async deleteEventExpense(id: number): Promise<null> {
    const response = await this.api.delete(`api/v1/expense/${id}`);

    if (response.status != 204) {
      throw new Error('Failed to delete event expense');
    }

    return response.data;
  }
}
