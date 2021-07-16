import { TestBed } from '@angular/core/testing';

import { ProfessionalServiceService } from './professional-service.service';

describe('ProfessionalServiceService', () => {
  let service: ProfessionalServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfessionalServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
