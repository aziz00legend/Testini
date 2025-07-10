import { Component } from '@angular/core';

@Component({
  selector: 'app-custom-stepper',
  templateUrl: './custom-stepper.component.html',
  styleUrl: './custom-stepper.component.scss'
})
export class CustomStepperComponent {
  currentStep = 0;
  steps = [
    { title: 'Step 1', content: 'Content for Step 1' },
    { title: 'Step 2', content: 'Content for Step 2' },
    { title: 'Step 3', content: 'Content for Step 3' }
  ];

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

  // Add a new step at the specified index
  addStep(index: number) {
    const newStep = { title: `Step ${this.steps.length + 1}`, content: `Content for Step ${this.steps.length + 1}` };
    
    // Insert the new step at the provided index
    this.steps.splice(index + 1, 0, newStep);
  }
}
