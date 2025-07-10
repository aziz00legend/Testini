import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ChallengeService } from '../services/challenge.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-template-gallery',
  templateUrl: './template.component.html',
  styleUrls: ['./template.component.scss']
})
export class TemplateComponent {
  constructor(
      private router: Router,
      private challengeService: ChallengeService
  ) {}

  

  types = [
    { label: 'Quiz', value: 'quiz' },
    { label: 'Game', value: 'game' },
    { label: 'Survey', value: 'survey' }
  ];

  levels = [
    { label: 'Kids', value: 'kids' },
    { label: 'Teens', value: 'teens' },
    { label: 'Adults', value: 'adults' }
  ];

  sortOptions = [
    { label: 'Newest', value: 'newest' },
    { label: 'Oldest', value: 'oldest' },
    { label: 'Most Popular', value: 'popular' }
  ];

  selectedType: string = 'quiz';
  selectedLevel: string = 'kids';
  selectedSort: string = 'newest';
  isTimeLimited: boolean = true;
  sortOrder: string = 'desc';
  displayMode: string = 'A';


  menuItems: string[] = ['Newest', 'Most Popular'];
  selectedItem: string | null = 'Newest';

  selectMenu(item: string): void {
    this.selectedItem = item;
  }

  createNewChallenge(instructorId: number): void {
    this.challengeService.createEmptyChallenge(instructorId).subscribe({
        next: (response) => {
            const challengeId = response.id; // Get the ID from the response
            this.router.navigate(['/inside/challenge-creator', challengeId]); // Navigate with the ID
        },
        error: (err) => {
            console.error('Error creating challenge:', err);
        }
    });
  }

  
}
