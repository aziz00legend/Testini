import { Component, Input, EventEmitter, Output, SimpleChanges, HostListener } from '@angular/core';
import { ClassroomService } from '../services/classroom.service';
import { ClassroomUpdateSaveDTO } from '../entities/ClassroomUpdateSaveDTO';
import { MessageService } from 'primeng/api';
import { ConfirmationService } from 'primeng/api';  // Import the ConfirmationService
import e from 'express';


@Component({
  selector: 'app-classroom-card',
  templateUrl: './classroom-card.component.html',
  styleUrls: ['./classroom-card.component.scss'],
})
export class ClassroomCardComponent {


  @Input() id: number = 0;
  @Input() title: string = 'Classroom Title';
  @Input() studentCount: number = 0;
  @Input() assignmentCount: number = 0;
  @Input() creationDate: Date = new Date();
  @Input() nextTestDate: Date = new Date();
  @Input() testTitle: string = 'No future tests are scheduled';
  @Input() titleColor: string = '#ccc';


  @Input() createMode: boolean = false;

  @Output() closeDialog: EventEmitter<void> = new EventEmitter<void>();

  @Output() updateComplete: EventEmitter<void> = new EventEmitter<void>();

  visible: boolean = false;
  dialogTitle: string = 'Edit Classroom';
  classroomTitle: string = '';
  selectedColor: string = '#FF6F61';
  colorOptions: string[] = [
    '#FF6F61', '#20B2AA', '#9370DB', '#FF8C00',
    '#32CD32', '#1E90FF', '#FF4500', '#50C878', '#ccc',
  ];

  constructor(private classroomService: ClassroomService, private messageService: MessageService, private confirmationService: ConfirmationService) { }

  ngOnInit(): void {
    if (this.createMode) {
      this.dialogTitle = 'New Classroom';
      this.visible = true;
    }
  }

  showDialog(event: Event) {
    event.stopPropagation();
    this.classroomTitle = this.title;
    this.selectedColor = this.titleColor;
    this.visible = true;
  }




  selectColor(color: string) {
    this.selectedColor = color;
  }

  stopEvent(event: Event) {
    event.stopPropagation();
  }

  saveChanges() {

    const classroom: ClassroomUpdateSaveDTO = {
      id: this.createMode ? 0 : this.id,
      title: this.classroomTitle,
      titleColor: this.selectedColor,
    };





    const request$ = this.createMode
      ? this.classroomService.saveClassroom(classroom, 1)
      : this.classroomService.updateClassroom(this.id, classroom);




    request$.subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: this.createMode ? 'Classroom created successfully.' : 'Classroom updated successfully.',
        });
        console.log(this.createMode)
        this.updateComplete.emit();
        this.visible = false; // Close dialog
        this.closeDialog.emit(); // Notify parent


      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: `Error ${this.createMode ? 'creating' : 'updating'} classroom.`,
        });
      },
    });
  }

  reversedSave() {
    this.updateComplete.emit();
    this.visible = false; // Close dialog
    this.closeDialog.emit();
  }


  @HostListener('document:click', ['$event'])
  onClick(event: MouseEvent) {
    const target = event.target as HTMLElement;

    // Check if the clicked element is inside the p-dialog-header-icons
    if (target.closest('.p-dialog-header-icons')) {
      this.reversedSave(); // Call the reject delete function
    }
  }



  ngOnChanges(changes: SimpleChanges): void {
    if (changes['createMode'] && this.createMode) {
      this.dialogTitle = 'New Classroom';
      this.classroomTitle = ''; // Reset form fields
      this.selectedColor = '#FF6F61'; // Default color
      this.visible = true; // Show dialog
    }
  }







  onConfirmDelete(event: Event) {
    event.stopPropagation();  // This is still needed here to stop the event from propagating to the parent component
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this classroom?',
      header: 'Confirm Deletion',
      icon: 'pi pi-exclamation-triangle',
      accept: () => this.onDeleteClassroom(),  // No need to pass event here
      reject: () => this.onRejectDelete(),    // No need to pass event here
    });
  }

  onDeleteClassroom() {
    // Directly delete classroom
    this.classroomService.deleteClassroom(this.id).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Classroom deleted successfully.',
        });
        this.updateComplete.emit(); // Notify parent about the update
      },
      error: (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error deleting classroom.',
        });
      },
    });
  }

  onRejectDelete() {
    // Show toast notification on cancellation
    this.messageService.add({
      severity: 'info',
      summary: 'Cancellation',
      detail: 'Classroom deletion was canceled.',
    });
    console.log('Deletion canceled');
    // Close the dialog immediately by triggering visibility control
    this.visible = false;
  }

}

