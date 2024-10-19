export interface Expense {
  id: number;
  date: string;
  description: string;
  amount: number;
}

export interface ExpansesResponse {
  expanses: Array<Expense>;
  total: number;
}
