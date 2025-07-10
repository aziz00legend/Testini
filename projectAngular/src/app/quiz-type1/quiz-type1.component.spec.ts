import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizType1Component } from './quiz-type1.component';

describe('QuizType1Component', () => {
  let component: QuizType1Component;
  let fixture: ComponentFixture<QuizType1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [QuizType1Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(QuizType1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
