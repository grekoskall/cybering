import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CyberingComponent } from './cybering.component';

describe('CyberingComponent', () => {
  let component: CyberingComponent;
  let fixture: ComponentFixture<CyberingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CyberingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CyberingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
