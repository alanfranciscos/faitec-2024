export interface FriendRequest {
  id: number;
  title: string;
  sendedAt: string;
}

export interface FriendRequestResponse {
  invite: Array<FriendRequest>;
}
