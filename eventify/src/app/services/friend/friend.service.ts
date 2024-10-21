import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import { FriendsHeader } from '../../domain/model/event/friendsHeader.model';

export interface Friend {
  email: string;
}
@Injectable({
  providedIn: 'root',
})
export class FriendService {
  constructor(private apiService: ApiService) {}
  api = this.apiService.getApi();

  async listFriends(offset: number, limit: number): Promise<FriendsHeader> {
    const response = await this.api.get<FriendsHeader>(
      `api/v1/friend?offset=${offset}&limit=${limit}`
    );

    if (response.status != 200) {
      throw new Error('Failed to fetch events');
    }

    return response.data;
  }

  async inviteFriend(data: Friend) {
    const formData = new FormData();
    formData.append('email', data.email);
    const response = await this.api.post(`/api/v1/friend/create`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    if (response.status != 201) {
      throw new Error('Failed to invite friend');
    }

    return response.data;
  }

  async deleteFriend(id: number) {
    await this.api.delete(`/api/v1/friend/${id}`);
  }

  async acceptRequest(id: number) {
    await this.api.put(`/api/v1/invite/list/friend/${id}/accept`);
  }

  async declineRequest(id: number) {
    await this.api.put(`/api/v1/invite/list/friend/${id}/reject`);
  }
}
