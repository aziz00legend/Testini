import { Component, OnInit } from '@angular/core';
import { FormGroup,FormControl } from '@angular/forms';
import { TreeNode } from 'primeng/api/treenode';

interface Assignment {
  StudentEmails: string[]; // Assuming StudentEmails is an array of strings
  Title: string;
  Classroom: string;
  sessionStart: string; // Use appropriate type, like Date, if applicable
  sessionEnd: string;   // Use appropriate type, like Date, if applicable
  creationDate: string; // Use appropriate type, like Date, if applicable
  status: string;       // Adjust the type as needed
}

@Component({
  selector: 'app-assignements',
  templateUrl: './assignements.component.html',
  styleUrl: './assignements.component.scss'
})
export class AssignementsComponent{

    value: string = 'name';

    stateOptions: any[] = [
        { label: 'Title', value: 'Title' },
        { label: 'Classroom', value: 'classroom' },
    ];

    



  menuItems: string[] = ['Newest', 'ALL', 'OPEN','CLOSED'];
  selectedItem: string | null = 'Newest';

  selectMenu(item: string) {
    this.selectedItem = item; // Set the selected item
  }


  displayMode = 'A'; // Control display mode
  assignements: any[] = [];
  
  ngOnInit() {
    this.assignements = [
      {
        title: 'Math Exam',
        classroom: 'Math 101',
        deadline: '2024-12-05',
        startingDate: '2024-12-01',
        NbSubmissions: 25,
        NbStudents: 30,
        status: 'Open'  // Assignment is open (currently ongoing)
      },
      {
        title: 'Physics Lab Report',
        classroom: 'Physics 102',
        deadline: '2024-12-10',
        startingDate: '2024-12-02',
        NbSubmissions: 0,  // No submissions yet (Upcoming)
        NbStudents: 20,
        status: 'Upcoming'  // Assignment hasn't started yet
      },
      {
        title: 'Chemistry Quiz',
        classroom: 'Chemistry 101',
        deadline: '2024-11-30',
        startingDate: '2024-11-20',
        NbSubmissions: 18,
        NbStudents: 20,
        status: 'Closed'  // Assignment is past deadline (Closed, red)
      },
      {
        title: 'Biology Assignment',
        classroom: 'Biology 101',
        deadline: '2024-12-07',
        startingDate: '2024-12-01',
        NbSubmissions: 15,
        NbStudents: 25,
        status: 'Open'  // Assignment is open (currently ongoing)
      },
      {
        title: 'Computer Science Project',
        classroom: 'CS 101',
        deadline: '2024-12-12',
        startingDate: '2024-12-03',
        NbSubmissions: 0,  // No submissions yet (Upcoming)
        NbStudents: 15,
        status: 'Upcoming'  // Assignment hasn't started yet
      },
      {
        title: 'History Paper',
        classroom: 'History 101',
        deadline: '2024-12-02',
        startingDate: '2024-11-28',
        NbSubmissions: 28,
        NbStudents: 30,
        status: 'Closed'  // Assignment is past deadline (Closed, red)
      },
      {
        title: 'English Essay',
        classroom: 'English 101',
        deadline: '2024-12-15',
        startingDate: '2024-12-05',
        NbSubmissions: 0,  // No submissions yet (Upcoming)
        NbStudents: 20,
        status: 'Upcoming'  // Assignment hasn't started yet
      },
      {
        title: 'Art Project',
        classroom: 'Art 101',
        deadline: '2024-12-08',
        startingDate: '2024-12-01',
        NbSubmissions: 22,
        NbStudents: 30,
        status: 'Open'  // Assignment is open (currently ongoing)
      },
      {
        title: 'Geography Assignment',
        classroom: 'Geography 101',
        deadline: '2024-11-29',
        startingDate: '2024-11-18',
        NbSubmissions: 10,
        NbStudents: 20,
        status: 'Closed'  // Assignment is past deadline (Closed, red)
      },
      {
        title: 'Economics Paper',
        classroom: 'Economics 101',
        deadline: '2024-12-04',
        startingDate: '2024-12-01',
        NbSubmissions: 0,  // No submissions yet (Upcoming)
        NbStudents: 18,
        status: 'Upcoming'  // Assignment hasn't started yet
      }
    ];
    
    
  }

  getStatusSeverity(status: string): 'secondary' | 'info' | 'danger' {
    switch (status) {
      case 'Upcoming':
        return 'secondary';
      case 'Open':
        return 'info';
      case 'Closed':
        return 'danger';
      default:
        return 'info'; // Default severity
    }
  }

  // Function to calculate the percentage completion
  getCompletionPercentage(assignement: any): number {
    return (assignement.NbSubmissions / assignement.NbStudents) * 100;
  }

  // Function to return the color class based on completion percentage
  getCompletionColor(assignement: any): string {
    const percentage = this.getCompletionPercentage(assignement);
    if (percentage <= 33) {
      return 'completion-red'; // Red for 0–33%
    } else if (percentage <= 66) {
      return 'completion-orange'; // Orange for 34–66%
    } else {
      return 'completion-green'; // Green for 67–100%
    }
  }

}
