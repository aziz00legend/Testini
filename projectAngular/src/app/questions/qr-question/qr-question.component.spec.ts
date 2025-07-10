import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QRQuestionComponent } from './qr-question.component';

describe('QRQuestionComponent', () => {
  let component: QRQuestionComponent;
  let fixture: ComponentFixture<QRQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [QRQuestionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(QRQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
