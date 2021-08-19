import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisementPostComponent } from './advertisement-post.component';

describe('AdvertisementPostComponent', () => {
  let component: AdvertisementPostComponent;
  let fixture: ComponentFixture<AdvertisementPostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdvertisementPostComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvertisementPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
