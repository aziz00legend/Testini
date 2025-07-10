import { TestBed } from '@angular/core/testing';

import { ChallengeService } from './challenge.service';
import { HttpClientModule } from '@angular/common/http';


describe('ChallengeService', () => {
  let service: ChallengeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule], // Add HttpClientModule here
      providers: [ChallengeService],
    });
    service = TestBed.inject(ChallengeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
