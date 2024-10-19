import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';

export interface EventInput {
  id?: number;
  eventname: string;
  eventdescription: string;
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
export class CreateEventServiceApi {
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

    if (data.eventname) {
      formData.append('eventname', data.eventname);
    }
    if (data.eventdescription) {
      formData.append('eventdescription', data.eventdescription);
    }
    if (data.local_name) {
      formData.append('local_name', data.local_name);
    }
    if (data.cep_address) {
      formData.append('cep_address', data.cep_address);
    }
    if (data.state_address) {
      formData.append('state_address', data.state_address);
    }
    if (data.city_address) {
      formData.append('city_address', data.city_address);
    }
    if (data.neighborhood_address) {
      formData.append('neighborhood_address', data.neighborhood_address);
    }
    if (data.number_address) {
      formData.append('number_address', data.number_address);
    }
    if (data.street_address) {
      formData.append('street_address', data.street_address);
    }
    if (data.complement_address) {
      formData.append('complement_address', data.complement_address);
    }

    if (data.latitude && data.latitude !== undefined) {
      formData.append('lat', data.latitude.toString());
    }
    if (data.longitude && data.longitude !== undefined) {
      formData.append('lng', data.longitude.toString());
    }
    if (data.date_start) {
      formData.append('date_start', data.date_start);
    }
    if (data.date_end) {
      formData.append('date_end', data.date_end);
    }
    if (data.stage) {
      formData.append('stage', data.stage);
    }
    // if (data.pix_key) {
    //   formData.append('pix_key', data.pix_key);
    // }

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
