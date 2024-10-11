import { Injectable } from '@angular/core';
import { UserInputCredential } from '../../domain/model/user.model';
import { ApiService } from '../api/api.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private apiService: ApiService) {}
  api = this.apiService.getApi();

  async createUserAccount(data: UserInputCredential): Promise<String> {
    const response = await this.api.post(`/api/v1/account`, {
      username: data.name,
      email: data.email,
      password: data.password,
    });

    const locationHeader = response.headers['location'];

    if (response.status != 201) {
      throw new Error('Failed to fetch organization data');
    }
    return locationHeader;
  }

  async updateImageProfile(userId: string, image: File): Promise<void> {
    const formData = new FormData();
    formData.append('image', image);

    const response = await this.api.patch(
      `/api/v1/account/${userId}/image`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );

    if (response.status != 200) {
      throw new Error('Failed to update image');
    }
  }
}
