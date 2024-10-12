export interface EventParticipants {
  id: number;
  name: string;
  aceptedAt: string;
  roleParticipate: string;
}

export interface EventParticipantsResponse {
  participants: Array<EventParticipants>;
}
