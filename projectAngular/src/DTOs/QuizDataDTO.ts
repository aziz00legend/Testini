export class QuizDataDTO {
    initialTimer: number;
    title: string;
    template: string;
    questions: Array<{
      text: string;
      options: string[];
      correctAnswer: string;
      points: number;
    }>;
  
    constructor(
        initialTimer: number = 10,
        title: string = "Default Quiz Title",
        template: string = "Standard",
        questions: Array<{
          text: string;
          options: string[];
          correctAnswer: string;
          points: number;
        }> = []
      ) {
        this.initialTimer = initialTimer;
        this.title = title;
        this.template = template;
        this.questions = questions; // Initialize with passed questions
      }
  }
  
  