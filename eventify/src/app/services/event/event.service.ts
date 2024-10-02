import { Injectable } from '@angular/core';
import { EventHeader } from '../../domain/model/event/eventHeader.model';
import { ApiService } from '../api/api.service';
import { Organization } from '../../domain/model/event/eventInformationOrganization.model';

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

  async gerInformationsOrganization(eventId: number): Promise<Organization> {
    const response = await this.api.get<Organization>(
      `api/v1/event/${eventId}/organization`
    );

    if (response.status != 200) {
      throw new Error('Failed to fetch events');
    }

    return response.data;
  }
}
