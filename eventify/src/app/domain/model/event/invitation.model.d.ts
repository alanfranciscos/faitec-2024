export interface EventInvitation {
  id: number;
  title: string;
  sendedAt: string;
}

export interface EventInvitationResponse {
  invite: Array<EventInvitation>;

  totalInvites: number;
}
