import { Injectable } from '@angular/core';
import { EventHeader } from '../../domain/model/event/eventHeader.model';
import { ApiService } from '../api/api.service';
import { OrganizationInfo } from '../../domain/model/event/organizationInfo.model';
import { TotalExpenses } from '../../domain/model/event/totalexpenses.model';
import { EventDate } from '../../domain/model/event/eventDate.model';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  constructor(private apiService: ApiService) {}
  api = this.apiService.getApi();

  async listEvents(offset: number, limit: number): Promise<EventHeader> {
    const response = await this.api.get<EventHeader>(
      `api/v1/event?offset=${offset}&limit=${limit}`
    );

    if (response.status != 200) {
      throw new Error('Failed to fetch events');
    }

    return response.data;
  }
  async getOrganizationData(eventId: string): Promise<OrganizationInfo> {
    const response = await this.api.get(`api/v1/event/${eventId}/organization`);
    if (response.status != 200) {
      throw new Error('Failed to fetch organization data');
    }
    return response.data;
  }

  async getTotalExpenses(id: string): Promise<TotalExpenses> {
    const response = await this.api.get(`api/v1/event/${id}/total-expanses`);
    if (response.status != 200) {
      throw new Error('Failed to fetch total expenses');
    }
    return response.data;
  }

  async getEventDate(id: string): Promise<EventDate> {
    const response = await this.api.get(`api/v1/event/${id}/dates`);
    if (response.status != 200) {
      throw new Error('Failed to fetch total expenses');
    }
    return response.data;
  }
}
