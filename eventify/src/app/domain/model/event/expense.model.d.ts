export interface Expense {
  date: string;
  description: string;
  amount: number;
}

export interface ExpansesResponse {
  expanses: Array<Expense>;
  total: number;
}
