import { Injectable } from '@angular/core';
import { ApiService } from '../api/api.service';
import { FriendsHeader } from '../../domain/model/event/friendsHeader.model';

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
}
