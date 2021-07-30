import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalinfoListComponent } from './personalinfo-list.component';

describe('PersonalinfoListComponent', () => {
  let component: PersonalinfoListComponent;
  let fixture: ComponentFixture<PersonalinfoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonalinfoListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalinfoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
