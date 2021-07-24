import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FullSignInComponent } from './full-sign-in.component';

describe('FullSignInComponent', () => {
  let component: FullSignInComponent;
  let fixture: ComponentFixture<FullSignInComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FullSignInComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FullSignInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
