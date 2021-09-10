import { TestBed } from '@angular/core/testing';

import { PersonalInfoServiceService } from './personal-info-service.service';

describe('PersonalInfoServiceService', () => {
  let service: PersonalInfoServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonalInfoServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
