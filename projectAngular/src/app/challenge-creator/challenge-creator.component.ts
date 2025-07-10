import { Component,Renderer2, ElementRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ChallengeService } from '../services/challenge.service';

interface Question {
  type: string;
  mode: 'view' | 'creation' | 'ask';
  data: any
}



@Component({
  selector: 'app-challenge-creator',
  templateUrl: './challenge-creator.component.html',
  styleUrl: './challenge-creator.component.scss'
})
export class ChallengeCreatorComponent {
  changeDetector: any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private messageService: MessageService,
              private confirmationService: ConfirmationService,
              private challengeService: ChallengeService) {}


  challengeId: number = 0;
  types: { label: string; value: string }[] = [];

  

  ngOnInit() {
    // Retrieve challenge ID from route
    this.challengeId = Number(this.route.snapshot.paramMap.get('id')) || 0;

    // Retrieve instructor ID from sessionStorage or a default
    sessionStorage.setItem('instructorId', '1');
    this.instructorId = Number(sessionStorage.getItem('instructorId')) || 0;

    console.log(this.challengeId);
    console.log(this.instructorId);

    // Load challenge with both IDs
    this.loadChallenge(this.challengeId, this.instructorId);

    this.types = [
      { label: 'Quiz', value: 'Quiz' },
      { label: 'Game', value: 'game' },
      { label: 'Survey', value: 'survey' },
      { label: 'Q/A', value: 'Q/A' }
    ];
  }

  // Initialize steppers with an empty array for questions in each stepper
   // Initial state for steppers
   currentStep = 0;

   steps : any[] = [
    //{ title: 'Question 1', question: null },
  ];

  instructorId!: number;

  
  challengeName: string = 'Default Challenge Name';  // Initialize with a default name
  challengeDesc: string = 'This is a challenge';

  // Initial template is set to 'default-template'
  selectedTemplate: string = 'default-template';
  

  /*editing challenge description */
  isEditingChallengeDesc: boolean = false;
  /*editing challenge name*/
  isEditingChallengeName: boolean = false;


  isChanged: boolean = false;



  selectedType: string | undefined;
  draggedItemType: string | null = null;
  droppedComponent: { type: string } | null = null;
  

  // Method to select a template
  selectTemplate(template: string): void {
    this.selectedTemplate = template;
    this.isChanged = true;
  }

  dragStart(type: string) {
    this.draggedItemType = type;
    console.log(type)
  }

  dragEnd() {
    this.draggedItemType = null;
  }

  // Handle drop event in the respective stepper
  drop(stepIndex: number) {
    this.isChanged = true;
    if (this.draggedItemType) {
      this.steps[stepIndex].question = {
        type: this.draggedItemType, // Create a new question object
        mode: 'creation', // Specify the mode here if needed
        data: {
          text: '', // Start with an empty string for the question text
          options: [], // Start with an empty array for options
          correctAnswerIndex: null // Initialize to null
        }
      };
      this.draggedItemType = null; // Reset dragged item
    }
  }

  // Function to add a new step after the current index
  addStep(index: number) {
    const newStep = {
      title: `Question ${index+1} `,
      question: null
    };
    this.isChanged = true;


    this.currentStep = index+1;
    
    // Insert new step after the current index
    this.steps.splice(index + 1, 0, newStep);

    // Optionally, update headers to match the new step order
    this.steps.forEach((step, i) => {
      if (step.title === `Question ${i} `) {
        step.title = `Question ${i + 1} `;
      }
    });
  }

  deleteStep(index: number, event: Event): void {
  
    // Remove the step from the array
    this.steps.splice(index, 1);
  
    // Update the question numbers after deletion
    this.steps.forEach((step, i) => {
      console.log(i+":"+ step.title);
      if (step.title.trim() == `Question ${i+2}`) {
        step.title = `Question ${i+1}`;
        console.log("inside if");
      }
    });
  
    // Mark that the data has been changed
    this.isChanged = true;
  }
  

  // Method to handle question updates from the child component
  onQuestionChange(updatedQuestion: any,i: any) {
    this.isChanged = true;
    this.steps[i].question = updatedQuestion; // Update the question in the parent
  }

  // Function to set the active step
  setStep(index: number): void {
    this.currentStep = index; // Set the current step to the clicked step
  }
  

  nextStep() {
    if (this.currentStep < this.steps.length - 1) {
      this.currentStep++;
    }
  }

  prevStep() {
    if (this.currentStep > 0) {
      this.currentStep--;
    }
  }
  
  getInvalidQuestion(step: any): string {
    // Check if there is no question or the question text is empty
    if (!step.question) {
      return 'No type selected';
    }

    // Check if question text is missing
    if (!step.question.data || !step.question.data.text) {
      return 'No question specified';
    }
  
    // Check if the question has fewer than 2 options
    if (step.question.type == "Quiz" && step.question.data.options.length < 2) {
      return ' (This question has less than 2 options)';
    }
  
    // Check if any option is empty
    if (step.question.type == "Quiz" && step.question.data.options.some((option: string) => option.trim() === '')) {
      return 'Some options are empty';
    }
    
    if (step.question.type == "Quiz" && step.question.data.correctAnswerIndex == null) {
      return 'The this quiz doesnt have a correct answer';
    }
    
  
    // If all checks pass, return an empty string
    return '';
  }
  

  
  Save(event: Event): void {
    this.challengeService.saveChanges(
      this.challengeId, // Pass challengeId
      this.challengeName,
      this.challengeDesc,
      this.selectedTemplate,
      this.steps,
      this.instructorId
    ).subscribe({
      next: (response) => {
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Changes Saved' });
        this.isChanged = false;
      },
      error: (error) => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to Save' });
        this.isChanged = true;
      }
    });
  }
  

  messages: any[] = [];
  Publish(event: Event) {
    console.log("publish pressed !!!")
    // Clear previous validation messages
    this.messages = [];

    // Validate each step and add issues to messages array
    this.steps.forEach((step,i) => {
      const validationError = this.getInvalidQuestion(step);
      console.log(i +" "+validationError)
      if (validationError) {
        this.messages.push({ severity: 'error', summary: `Error in ${step.title}`, detail: validationError });
      }
    });

    // Set dialog properties dynamically based on whether there are validation errors
    let dialogMessage = 'This action is irreversible. You can\'t change a challenge after you publish it.';
    let dialogHeader = 'Confirmation';
    let dialogIcon = 'pi pi-exclamation-triangle';
    let rejectButtonClass = 'reject-button-red';  // Default reject button style
    let acceptButtonLabel = 'yes';
    let acceptButtonIcon = 'pi pi-check';

    if (this.messages.length > 0) {
      // If there are validation issues, change the dialog content
      dialogMessage = 'There are validation errors. Please correct them before proceeding.';
      dialogHeader = 'Validation Errors';
      dialogIcon = 'pi pi-times-circle';
      rejectButtonClass = 'reject-button-hidden';
      acceptButtonLabel = 'OK';
      acceptButtonIcon = 'none';
    }

    // Show the confirmation dialog with updated message, header, and icon
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: dialogMessage,
      header: dialogHeader,
      accept: () => {
        // Only proceed with publishing if there are no validation errors
        if (this.messages.length === 0) {
          // Check if there are unsaved changes
          if (this.isChanged) {
            this.Save(event);

            // After saving, check if changes were saved successfully
            if (!this.isChanged) {
              // If save was successful, proceed to publish
              this.challengeService.publishChallenge(this.challengeId).subscribe({
                next: () => {
                  this.messageService.add({ severity: 'info', summary: 'Published', detail: 'Challenge has been published successfully.' });
                  this.router.navigate(['/inside/challenges']);
                },
                error: (error) => {
                  this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to Publish' });
                  console.error('Publish Error:', error);
                }
              });
            } else {
              // If save failed, do not proceed to publish
              this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Save Failed. Publish aborted.' });
            }
          } else {
            // If no changes to save, directly proceed to publish
            this.challengeService.publishChallenge(this.challengeId).subscribe({
              next: () => {
                this.messageService.add({ severity: 'info', summary: 'Published', detail: 'Challenge has been published successfully.' });
                this.router.navigate(['/inside/challenges']);
              },
              error: (error) => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to Publish' });
                console.error('Publish Error:', error);
              }
            });
          }
        }
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Cancelled', detail: 'Publishing cancelled.' });
      },
      rejectButtonStyleClass: rejectButtonClass,  // Apply red color to reject button if there are errors
      acceptLabel: acceptButtonLabel,
      acceptIcon: acceptButtonIcon
    });
    
  }

  /*editing the title of question */
  editingTitleIndex: number | null = null; // Holds the index of the title being edited, or null if not editing
  editingTitle: string = '';

  // Method to activate edit mode for a specific title
  editTitle(index: number) {
    this.editingTitleIndex = index; // Set the current index to edit
    this.editingTitle = this.steps[index].title; // Load the current title into editingTitle
    this.setStep(index)
    this.isChanged = true;
  }
  

  // Method to save the edited title
  saveTitle(index: number) {
    // Update the title in the steps array with the edited title
    this.steps[index].title = this.editingTitle;
  
    // Exit edit mode by setting editingTitleIndex back to null
    this.editingTitleIndex = null;
    this.isChanged = true;
  }


  saveChallengeName() {
    this.challengeName = this.challengeName.trim();
    this.isEditingChallengeName = false;
  }

  startEditingName(){
    this.isEditingChallengeName = true;
    this.isChanged = true;
  }


  saveChallengeDesc() {
    this.challengeDesc = this.challengeDesc.trim();
    this.isEditingChallengeDesc = false;
  }

  startEditingDesc(){
    this.isEditingChallengeDesc = true;
    this.isChanged = true;
  }

  //View Mode
  viewActivated: boolean = false;

  ActivateView() {
    this.viewActivated = true;
  }


  // Loading data from database
  // Load challenge data from the service
  loadChallenge(challengeId: number, instructorId: number): void {
    this.challengeService.loadChallenge(challengeId, instructorId).subscribe({
      next: (response) => {
        console.log("the response is:")
        console.log(response);

        // Update the local variables with the received data
        this.challengeName = response.title || this.challengeName;
        this.challengeDesc = response.description || this.challengeDesc;
        this.selectedTemplate = response.templateName || this.selectedTemplate;
        this.steps = response.questions || this.steps;
      },
      error: (err) => {
        console.error('Error loading challenge:', err);
      }
    });
  }
}
