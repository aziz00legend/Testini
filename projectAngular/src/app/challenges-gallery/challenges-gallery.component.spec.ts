import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengesGalleryComponent } from './challenges-gallery.component';

describe('ChallengesGalleryComponent', () => {
  let component: ChallengesGalleryComponent;
  let fixture: ComponentFixture<ChallengesGalleryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChallengesGalleryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ChallengesGalleryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
