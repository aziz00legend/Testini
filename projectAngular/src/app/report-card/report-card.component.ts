import { Component, OnInit } from '@angular/core';
import { SessionDTO } from '../entities/Session';
import { SessionService } from '../services/session.service';
import { MergeDTO, OperationM } from '../entities/MergeDTO';
import { MergeService } from '../services/merge.service';

@Component({
  selector: 'app-report-card',
  templateUrl: './report-card.component.html',
  styleUrls: ['./report-card.component.scss']
})
export class ReportCardComponent implements OnInit {
  breadcrumbItems!: any[];
  sessions!: SessionDTO[];
  students!: { email: string;[sessionTitle: string]: number | string }[];
  displayDialog: boolean = false;
  selectedResult: { id: number; email: string } | null = null;
  mergeSessionsDialog: boolean = false;
  selectedSessions: SessionDTO[] = [];
  selectedNames: string[] = [];

  selectedOperation: string = '';
  coefficients: { [sessionTitle: string]: number } = {}; // Coefficient input values for each session
  sessionSuggestions: string[] = []; // Suggestions for autocomplete
  operationOptions: string[] = ['Max', 'Avg', 'Coef Special'];
  mergeTitle: string = '';

  mergeDtos!: MergeDTO[];

  constructor(private sessionService: SessionService, private mergeService: MergeService) { }

  ngOnInit() {
    this.breadcrumbItems = [
      { label: 'Tableau de bord' },
      { label: 'Récapitulatif des scores' },
      { label: 'MS - Mathématiques - 2ème Période' }
    ];

    this.loadMerge();
    this.loadSessions();

    console.log(this.mergeDtos);
  }

  loadMerge() {
    this.mergeService.getMergesByClassroomId(1).subscribe({
      next: (data: MergeDTO[]) => {
        this.mergeDtos = data;
      },
      error: (error) => {
        console.error('Error fetching merge DTOs:', error);
      }
    });
  }


  loadSessions() {
    this.sessionService.getAllSessions().subscribe(
      (sessions: SessionDTO[]) => {
        this.sessions = sessions;

        this.sessionSuggestions = sessions.map(session => session.title);

        const allStudentEmails = new Set<string>();
        sessions.forEach(session =>
          session.results.forEach(result => allStudentEmails.add(result.studentEmail))
        );

        this.students = Array.from(allStudentEmails).map(email => {
          const studentData: { email: string;[sessionTitle: string]: number | string } = { email };
          sessions.forEach(session => {
            const result = session.results.find(r => r.studentEmail === email);
            studentData[session.title] = result ? result.score : 'Absent';
          });
          return studentData;
        });
      },
      error => console.error('Error loading sessions:', error)
    );
  }

  openMergeDialog() {
    this.mergeSessionsDialog = true;
    this.selectedSessions = [];
    this.selectedNames = [];
    this.selectedOperation = '';
    this.coefficients = {};
    this.mergeTitle = '';
  }

  onSessionSelected() {
    this.selectedSessions = this.sessions.filter(session =>
      this.selectedNames.includes(session.title)
    );
  }

  logSelectedOptions() {
    // Create the MergeDTO object with the selected form data
    const mergeDTO: MergeDTO = {
      id: 0, // Assign a default or actual ID if available
      title: this.mergeTitle,
      operation: this.selectedOperation as OperationM, // Cast to OperationM enum
      idSessions: this.selectedSessions.map(session => session.id),
      coefSessions: this.selectedOperation === 'Coef Special'
        ? this.selectedSessions.map(session => this.coefficients[session.title] || 1)
        : []
    };

    console.log('MergeDTO:', mergeDTO); // Log the MergeDTO object
    this.mergeSessionsDialog = false; // Close dialog after logging
  }

  search(event: { query: string }) {
    const query = event.query.toLowerCase();
    this.sessionSuggestions = this.sessions
      .map(session => session.title)
      .filter(title => title.toLowerCase().includes(query));
  }

  openDialogByEmail(session: SessionDTO, studentEmail: string) {
    const result = session.results.find((r) => r.studentEmail === studentEmail);
    if (result) {
      this.selectedResult = { id: result.id, email: result.studentEmail };
      this.displayDialog = true;
    }
  }
}
