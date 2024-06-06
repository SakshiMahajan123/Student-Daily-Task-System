import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttemptestsComponent } from './attemptests.component';

describe('AttemptestsComponent', () => {
  let component: AttemptestsComponent;
  let fixture: ComponentFixture<AttemptestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AttemptestsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AttemptestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
