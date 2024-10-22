import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';

@Injectable({
  providedIn: 'root',
})
export class DeleteEventService {
  constructor(private apiService: ApiService) {}
  api = this.apiService.getApi();

  async deleteEvent(id: number): Promise<null> {
    const response = await this.api.delete(`/api/v1/event/${id}`);

    if (response.status != 204) {
      throw new Error('Failed to delete event expense');
    }

    return response.data;
  }

  async deleteEventMember(eventId: number, userId: number): Promise<null> {
    const response = await this.api.delete(`/api/v1/participate/${eventId}/${userId}`);

    if (response.status != 204) {
      throw new Error('Failed to delete event member');
    }

    return response.data;
  }
}
