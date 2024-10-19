import { TestBed } from '@angular/core/testing';

import { DeleteEventExpenseService } from './delete-event-expense.service';

describe('DeleteEventExpenseService', () => {
  let service: DeleteEventExpenseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeleteEventExpenseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
