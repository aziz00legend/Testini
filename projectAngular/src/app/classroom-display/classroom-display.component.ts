import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ClassroomService } from '../services/classroom.service';
import { ClassroomDTO } from '../entities/ClassroomDTO';
@Component({
  selector: 'app-classroom-display',
  templateUrl: './classroom-display.component.html',
  styleUrls: ['./classroom-display.component.scss']
})
export class ClassroomDisplayComponent implements OnInit {
  menuItems: string[] = ['All', 'Newest', 'Closest Test'];
  selectedItem: string | null = 'All';
  classrooms: ClassroomDTO[] = [];
  showCreateDialog: boolean = false;

  constructor(private router: Router, private classroomService: ClassroomService) { }

  ngOnInit(): void {
    this.loadClassrooms();
  }

  loadClassrooms(): void {
    const instructorId = 1; // Example instructor ID
    this.classroomService.getAllClassroomsByInstructorId(instructorId).subscribe({
      next: (data: ClassroomDTO[]) => {
        this.classrooms = data;
      },
      error: (error) => {
        console.error('Error fetching classrooms:', error);
      }
    });
  }

  // Reload classrooms when an update event is received
  onChildUpdate(): void {
    this.loadClassrooms(); // Reload data
  }

  navigateTo(classroomId: number) {
    this.router.navigate(['/inside/report-card', classroomId]);
  }

  selectMenu(item: string): void {
    this.selectedItem = item;
  }










  triggerNewClassroom(): void {
    this.showCreateDialog = true; // Show the dialog for creating a new classroom
  }

  closeCreateDialog(): void {
    this.showCreateDialog = false; // Reset dialog visibility
  }
}
