// answer.dto.ts
export class AnswerDTO {
    timer: number;
    chosenAnswer: string;
    isCorrect: boolean;
    timestamp: Date;
  
    constructor(timeLeft: number = 0,chosenAnswer: string = '', timestamp: Date = new Date(), isCorrect: boolean = false) {
      this.timer = timeLeft;
      this.chosenAnswer = chosenAnswer;
      this.isCorrect = isCorrect;
      this.timestamp = timestamp;
    }
  }
  