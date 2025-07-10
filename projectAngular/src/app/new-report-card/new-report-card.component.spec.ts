import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewReportCardComponent } from './new-report-card.component';

describe('NewReportCardComponent', () => {
  let component: NewReportCardComponent;
  let fixture: ComponentFixture<NewReportCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewReportCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NewReportCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
