import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassroomDisplayComponent } from './classroom-display.component';

describe('ClassroomDisplayComponent', () => {
  let component: ClassroomDisplayComponent;
  let fixture: ComponentFixture<ClassroomDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ClassroomDisplayComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClassroomDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
