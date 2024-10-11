export interface FriendsHeader {
  friends: Array<FriendsContent>;
  total: number;
}

export interface FriendsContent {
  id: number;
  username: string;
  image: string;
  dateStartFriendship: string;
}
