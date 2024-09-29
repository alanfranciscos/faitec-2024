export interface EventHeader {
  events: Array<EventContent>;
  total: number;
}

export interface EventContent {
  id: string;
  title: string;
  information: string;
  dateStart: string;
  dateEnd: string;
  profileImage: string;
  stage: string;
}
