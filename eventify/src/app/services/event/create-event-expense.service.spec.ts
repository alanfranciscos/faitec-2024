import { TestBed } from '@angular/core/testing';

import { CreateEventExpenseService } from './create-event-expense.service';

describe('CreateEventExpenseService', () => {
  let service: CreateEventExpenseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateEventExpenseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
