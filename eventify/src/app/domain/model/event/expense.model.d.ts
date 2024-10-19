export interface Expense {
  meetup_id?: number;
  id: number;
  date: string;
  description: string;
  amount: number;
}

export interface ExpansesResponse {
  expanses: Array<Expense>;
  total: number;
}
