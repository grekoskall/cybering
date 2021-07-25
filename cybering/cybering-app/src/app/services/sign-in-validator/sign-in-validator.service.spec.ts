import { TestBed } from '@angular/core/testing';

import { SignInValidatorService } from './sign-in-validator.service';

describe('SignInValidatorService', () => {
  let service: SignInValidatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SignInValidatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
