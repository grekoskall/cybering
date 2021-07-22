import { TestBed } from '@angular/core/testing';

import { ProfessionalService } from './professional-service.service';

describe('ProfessionalServiceService', () => {
  let service: ProfessionalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfessionalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
