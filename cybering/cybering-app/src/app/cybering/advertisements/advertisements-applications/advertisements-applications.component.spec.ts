import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisementsApplicationsComponent } from './advertisements-applications.component';

describe('AdvertisementsApplicationsComponent', () => {
  let component: AdvertisementsApplicationsComponent;
  let fixture: ComponentFixture<AdvertisementsApplicationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdvertisementsApplicationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvertisementsApplicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
