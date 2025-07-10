import { Component, OnInit, ViewChild } from '@angular/core';
import { SessionDTO } from '../entities/Session';
import { SessionService } from '../services/session.service';
import { MergeDTO, OperationM } from '../entities/MergeDTO';
import { MergeService } from '../services/merge.service';
import { SessionTreeComponent } from '../session-tree/session-tree.component';
import { ActivatedRoute } from '@angular/router';
import { ClassroomService } from '../services/classroom.service';
import { ClassroomDTO } from '../entities/ClassroomDTO';
import { error } from 'console';
import { ChallengeService } from '../services/challenge.service';
import { ChallengeDisplayDTO } from '../entities/ChallengeDisplayDTO';
import { SessionSaveUpdateDTO } from '../entities/sessionSaveUpdateDTO';

@Component({
  selector: 'app-new-report-card',
  templateUrl: './new-report-card.component.html',
  styleUrls: ['./new-report-card.component.scss']
})
export class NewReportCardComponent implements OnInit {
  @ViewChild(SessionTreeComponent) childComponent!: SessionTreeComponent;

  breadcrumbItems!: any[];
  sessions!: SessionDTO[];
  mergeSessionsDialog: boolean = false;
  selectedSessions: SessionDTO[] = [];
  selectedNames: string[] = [];

  selectedOperation: string = '';
  coefficients: { [sessionTitle: string]: number } = {}; // Coefficient input values for each session
  sessionSuggestions: string[] = []; // Suggestions for autocomplete
  operationOptions: { label: string, value: string }[] = [
    { label: 'Maximum Score', value: OperationM.MAX },
    { label: 'Average Score', value: OperationM.AVG },
    { label: 'Custom Coefficients', value: OperationM.COEFS }
  ]; // Descriptive options for dropdown
  mergeTitle: string = '';

  mergeDtos!: MergeDTO[];

  OperationM = OperationM; // Exposing OperationM enum to the template
  iDSessions: any;
  classroomId: number = 1;
  classroomDTO: ClassroomDTO = {
    id: 0,
    title: '',
    titleColor: '',
    creationDate: new Date(),
    studentCount: 0,
    assignmentCount: 0,
    nextTestDate: new Date(),
    testTitle: ''
  };

  challenges: ChallengeDisplayDTO[] = [];

  newSessionDialog: boolean = false;
  newSession: SessionSaveUpdateDTO = {
    id: 0,
    title: '',
    sessionCode: '',
    startTime: '',
    endTime: '',
    createdAt: new Date()
  };

  selectedChallenge!: ChallengeDisplayDTO;
  filteredChallenges: ChallengeDisplayDTO[] = [];


  constructor(private sessionService: SessionService, private mergeService: MergeService, private route: ActivatedRoute, private classroomService: ClassroomService,
    private challengeService: ChallengeService
  ) { }

  ngOnInit(): void {
    this.breadcrumbItems = [
      { label: 'Tableau de bord' },
      { label: 'Récapitulatif des scores' },
      { label: 'MS - Mathématiques - 2ème Période' }
    ];

    this.classroomId = Number(this.route.snapshot.paramMap.get('id')) || 0;

    this.classroomService.getClassroomById(this.classroomId).subscribe({
      next: (classroom) => {
        this.classroomDTO = classroom;
        this.breadcrumbItems = [
          { label: 'Tableau de bord' },
          { label: 'Récapitulatif des scores' },
          { label: this.classroomDTO.title }
        ];

      },
      error: (err) => {
        console.error('Erreur lors de la récupération de la classe :', err);
        // Tu peux afficher un message ou faire une redirection ici
      }
    });


    const instructorId = 1; // Remplacer par une valeur dynamique si besoin
    console.log(this.challenges);
    this.challengeService.getChallengesByInstructor(instructorId).subscribe({
      next: (data: ChallengeDisplayDTO[]) => {
        // Ne conserver que les challenges publiés
        this.challenges = data.filter(challenge => challenge.status === 'PUBLISHED');



        // Vérification avant affichage dans la console
        if (this.challenges.length > 0) {
          console.log(this.challenges[0]);
        } else {
          console.log('Aucun challenge publié trouvé.');
        }
      },
      error: (error) => {
        console.error('Erreur lors de la récupération des challenges :', error);
      }
    });
  }




  updateSessions(sessions: SessionDTO[]) {
    this.sessions = sessions;
  }
  /*
    loadSessions() {
      this.sessionService.getAllSessions().subscribe(
        (sessions: SessionDTO[]) => {
          this.sessions = sessions;
   
          console.log(this.sessions)
          this.sessionSuggestions = sessions.map(session => session.title);
   
        },
        error => console.error('Error loading sessions:', error)
      );
    }
  */


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

  search(event: { query: string }) {
    const query = event.query.toLowerCase();
    this.sessionSuggestions = this.sessions
      .map(session => session.title)
      .filter(title => title.toLowerCase().includes(query));
  }

  saveMerge() {



    const mergeDTO: MergeDTO = {
      id: 0, // Default ID (the backend might auto-generate this)
      title: this.mergeTitle,
      operation: this.selectedOperation as OperationM, // Cast to OperationM enum
      idSessions: this.selectedSessions.map(session => session.id),

      coefSessions: this.selectedOperation === OperationM.COEFS
        ? this.selectedSessions.map(session => this.coefficients[session.title] || 1)
        : []
    };


    const classroomId = 1; // Replace with the actual classroom ID if needed
    this.mergeService.createMerge(mergeDTO, classroomId).subscribe(
      (response: MergeDTO) => {
        console.log('Merge saved successfully:', response);
        this.childComponent.resendSessions();
        this.mergeSessionsDialog = false; // Close the dialog on success
      },
      error => console.error('Error saving merge:', error)
    );


  }





  openNewSessionDialog() {
    this.newSessionDialog = true;
    this.newSession = {
      id: 0,
      title: '',
      sessionCode: '',
      startTime: '',
      endTime: '',
      createdAt: new Date()
    };
    this.selectedChallenge = {} as ChallengeDisplayDTO;
  }

  filterChallenges(event: { query: string }) {
    const query = event.query.toLowerCase();
    this.filteredChallenges = this.challenges.filter(ch =>
      ch.title.toLowerCase().includes(query)
    );
  }

  saveNewSession() {
    if (!this.selectedChallenge?.id) {
      console.error('No challenge selected');
      return;
    }

    this.sessionService.createSession(this.newSession, this.selectedChallenge.id, this.classroomId).subscribe({
      next: () => {
        console.log('Session created successfully');
        this.childComponent.resendSessions(); // Refresh session tree
        this.newSessionDialog = false;
      },
      error: (err) => {
        console.error('Error creating session:', err);
      }
    });
  }
}
