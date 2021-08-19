import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisementsHomeComponent } from './advertisements-home.component';

describe('AdvertisementsHomeComponent', () => {
  let component: AdvertisementsHomeComponent;
  let fixture: ComponentFixture<AdvertisementsHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdvertisementsHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvertisementsHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
