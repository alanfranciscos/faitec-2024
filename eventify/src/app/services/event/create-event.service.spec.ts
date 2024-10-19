import { TestBed } from '@angular/core/testing';

import { CreateEventServiceApi } from './create-event.service-api';

describe('CreateEventService', () => {
  let service: CreateEventServiceApi;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateEventServiceApi);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
