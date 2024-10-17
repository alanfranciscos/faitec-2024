import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';

export interface EventInput {
  id?: number;
  eventname: string;
  eventdescription: string;
  createdAt: string;
  local_name: string;
  cep_address: string;
  state_address: string;
  city_address: string;
  neighborhood_address: string;
  number_address: string;
  street_address: string;
  complement_address: string;
  latitude: number;
  longitude: number;
  date_start: string;
  date_end: string;
  stage: string;
  pix_key: string;
}

@Injectable({
  providedIn: 'root',
})
export class CreateEventService {
  constructor(private apiService: ApiService) {}
  api = this.apiService.getApi();

  async createEvent(
    data: EventInput,
    eventImage: File | null
  ): Promise<String> {
    const formData = new FormData();
    if (eventImage) {
      formData.append('image', eventImage);
    }
    formData.append('event', JSON.stringify(data));
    const response = await this.api.post(`/api/v1/event`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    const locationHeader = response.headers['location'];

    if (response.status !== 201) {
      throw new Error('Failed to create event');
    }
    return locationHeader;
  }
}
