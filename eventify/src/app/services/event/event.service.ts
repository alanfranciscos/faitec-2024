import { Injectable } from '@angular/core';
import { EventHeader } from '../../domain/model/event/eventHeader.model';
import { ApiService } from '../api/api.service';
import { OrganizationInfo } from '../../domain/model/event/organizationInfo.model';
import { TotalExpenses } from '../../domain/model/event/totalexpenses.model';
import { EventDate } from '../../domain/model/event/eventDate.model';
import { EventLocation } from '../../domain/model/event/eventLocalization.model';
import { PaymentApproach } from '../../domain/model/event/paymentApproach.model';
import { ExpansesResponse } from '../../domain/model/event/expense.model';
import { EventParticipantsResponse } from '../../domain/model/event/eventparticipants.model';
import { EventInvitationResponse } from '../../domain/model/event/invitation.model';
import { FriendRequestResponse } from '../../domain/model/event/friend_request.model';
import { ExpenseInput } from './create-event-expense.service';
import { EventData } from '../../domain/model/event/eventData.model';

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

  async getEventData(eventId: number) {
    const response = await this.api.get<EventData>(`/api/v1/event/${eventId}`);

    if (response.status != 200) {
      throw new Error('Failed to fetch event data');
    }

    return response.data;
  }

  async getEventDataImage(eventId: number) {
    const response = await this.api.get<string>(
      `/api/v1/event/image/${eventId}`
    );
    if (response.status != 200) {
      throw new Error('Failed to fetch event image');
    }

    return response.data;
  }

  async listParticipants(
    eventId: string,
    offset: number,
    limit: number
  ): Promise<EventParticipantsResponse> {
    const response = await this.api.get(
      `api/v1/event/${eventId}/participants?offset=${offset}&limit=${limit}`
    );
    if (response.status != 200) {
      throw new Error('Failed to fetch organization data');
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

  async getTotalExpenses(eventId: string): Promise<TotalExpenses> {
    const response = await this.api.get(
      `api/v1/event/${eventId}/total-expanses`
    );
    if (response.status != 200) {
      throw new Error('Failed to fetch total expenses');
    }
    return response.data;
  }
  async getEventExpanses(
    eventId: string,
    offset: number,
    limit: number
  ): Promise<ExpansesResponse> {
    const response = await this.api.get<ExpansesResponse>(
      `api/v1/event/${eventId}/expanses?offset=${offset}&limit=${limit}`
    );

    if (response.status !== 200) {
      throw new Error('Failed to fetch event expanses');
    }

    return response.data;
  }

  async getEventDate(eventId: string): Promise<EventDate> {
    const response = await this.api.get(`api/v1/event/${eventId}/dates`);
    if (response.status != 200) {
      throw new Error('Failed to fetch total expenses');
    }
    return response.data;
  }
  async getEventLocation(eventId: string): Promise<EventLocation> {
    const response = await this.api.get(`api/v1/event/${eventId}/localization`);
    if (response.status != 200) {
      throw new Error('Failed to fetch total expenses');
    }
    return response.data;
  }
  async getPaymentApproach(eventId: string): Promise<PaymentApproach> {
    const response = await this.api.get(`api/v1/event/${eventId}/payment`);
    if (response.status != 200) {
      throw new Error('Failed to fetch total expenses');
    }
    return response.data;
  }

  async getEventInvitation(): Promise<EventInvitationResponse> {
    const response = await this.api.get<EventInvitationResponse>(
      `/api/v1/invite/list/event`
    );
    if (response.status !== 200) {
      throw new Error('Failed to fetch event invitation');
    }
    return response.data;
  }
  async getFriendRequest(): Promise<FriendRequestResponse> {
    const response = await this.api.get<FriendRequestResponse>(
      `api/v1/invite/list/friend`
    );
    if (response.status !== 200) {
      throw new Error('Failed to fetch event invitation');
    }
    return response.data;
  }

  async acceptRequest(id: number) {
    await this.api.put(`/api/v1/invite/list/event/${id}/accept`);
  }

  async declineRequest(id: number) {
    await this.api.put(`/api/v1/invite/list/event/${id}/reject`);
  }

  async updateEventExpense(id: number, expenseData: ExpenseInput) {
    const formData = new FormData();

    formData.append('about', expenseData.about);
    formData.append('cost', expenseData.cost);
    formData.append('created_at', expenseData.eventDate!);

    const response = await this.api.put(`/api/v1/expense/${id}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    if (response.status != 200) {
      throw new Error('Failed to edit event expense');
    }
  }

  async inviteMember(id: string, email: string): Promise<void> {
    const formData = new FormData();

    formData.append('event_id', id);
    formData.append('email', email);
    const response = await this.api.post(`/api/v1/participate`, formData);

    if (response.status != 201) {
      throw new Error('Failed to add a new member');
    }
  }
}
