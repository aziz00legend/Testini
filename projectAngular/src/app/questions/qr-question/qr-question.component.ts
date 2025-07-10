import { Component, EventEmitter, Input, Output } from '@angular/core';

interface Question {
  type: 'Q/A'; // Type is Q/A
  number: number; // The question number
  data: {
    text: string; // The main question text
    description?: string; // Simple description as a single string
  };
}

@Component({
  selector: 'app-qr-question',
  templateUrl: './qr-question.component.html',
  styleUrls: ['./qr-question.component.scss']
})
export class QRQuestionComponent {
  
  ngOnInit(): void {
    if (this.questionData) {
      this.question = { ...this.questionData }; // Use spread operator to copy data
    }
  }

 @Input() questionData: any;

  QADataView: Question = {
    type: 'Q/A',
    number: 1,
    data: {
      text: 'How do you feel right now?',
      description: 'Describe your current state of mind.'
    }
  };

  @Input() mode?: 'view' | 'creation' | 'ask';
  @Input() number!: number;

  @Input() template?: string;

  @Output() questionChange = new EventEmitter<Question>();

  // Default question structure for Q/A type
  
  question: Question = {
    type: 'Q/A',
    number: 1,
    data: {
      text: '', // Initialize question text
      description: '' // Simple description as a single string
    }
  };

  // Update the main question text
  updateQuestionText(questionText: string) {
    this.question.data.text = questionText;
    console.log('updating qestion text: '+ this.question.data.text)

    this.questionChange.emit(this.question); // Emit the updated question
  }

  // Update the description text
  updateDescription(description: string) {
    this.question.data.description = description;
    this.questionChange.emit(this.question); // Emit the updated question
  }

  // Answer for the "ask" mode
  answer: string = '';
}
