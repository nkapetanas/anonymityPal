import { TestBed } from '@angular/core/testing';

import { QueryPrestoService } from './query-presto-service.service';

describe('QueryPrestoService', () => {
  let service: QueryPrestoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QueryPrestoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
