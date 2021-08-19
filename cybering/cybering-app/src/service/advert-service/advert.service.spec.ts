import { TestBed } from '@angular/core/testing';

import { AdvertService } from './advert.service';

describe('AdvertService', () => {
  let service: AdvertService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdvertService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
