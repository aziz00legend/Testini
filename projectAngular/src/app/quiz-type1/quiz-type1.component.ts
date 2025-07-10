import { Component, Input, OnInit } from '@angular/core';

import { AnswerDTO } from '../../DTOs/AnswerDTO'
import { QuizDataDTO } from '../../DTOs/QuizDataDTO'


@Component({
  selector: 'app-quiz-type1',
  templateUrl: './quiz-type1.component.html',
  styleUrls: ['./quiz-type1.component.scss'] // Ensure styles are linked
})
export class QuizType1Component implements OnInit {
  @Input() mode: 'ask' | 'view' = 'ask';
  @Input() challengeId: number | null = null;   // Default to empty string
  @Input() submissionId: number | null = null;   // Default to empty string
  
  quizData = [
    {
      text: "What is Askin Nakk Le Vaar's ability?",
      options: ["The Deathdealing", "The Almighty", "The Compulsory", "The Heat"],
      correctAnswer: "The Deathdealing",
      points: 2
    },
    {
      text: "What is Yhwach's ability?",
      options: ["The Almighty", "The Visionary", "The Glutton", "The Balance"],
      correctAnswer: "The Almighty",
      points: 2
    },
    {
      text: "What is Jugram Haschwalth's ability?",
      options: ["The Miracle", "The Balance", "The Love", "The Zombie"],
      correctAnswer: "The Balance",
      points: 2
    },
    {
      text: "What is Pernida Parnkgjas's ability?",
      options: ["The Compulsory", "The Heat", "The Glutton", "The X-Axis"],
      correctAnswer: "The Compulsory",
      points: 2
    },
    {
      text: "What is Bambietta Basterbine's ability?",
      options: ["The Explode", "The Overkill", "The Almighty", "The Fear"],
      correctAnswer: "The Explode",
      points: 2
    },
    {
      text: "What is As Nodt's ability?",
      options: ["The Fear", "The Visionary", "The Nausea", "The Compulsory"],
      correctAnswer: "The Fear",
      points: 2
    },
    {
      text: "What is Lille Barro's ability?",
      options: ["The Almighty", "The Love", "The X-Axis", "The Yourself"],
      correctAnswer: "The X-Axis",
      points: 2
    },
    {
      text: "What is Gremmy Thoumeaux's ability?",
      options: ["The Love", "The Visionary", "The Zombie", "The Miracle"],
      correctAnswer: "The Visionary",
      points: 2
    },
    {
      text: "What is Bazz-B's ability?",
      options: ["The Glutton", "The Heat", "The Nausea", "The Almighty"],
      correctAnswer: "The Heat",
      points: 2
    },
    {
      text: "What is Gerard Valkyrie's ability?",
      options: ["The Visionary", "The Miracle", "The Deathdealing", "The X-Axis"],
      correctAnswer: "The Miracle",
      points: 2
    }
  ];


  quiz!: QuizDataDTO;
  answers!: AnswerDTO[];

  currentQuestionIndex: number = 0;
  selectedAnswer: string | null = null; // Initialize selectedAnswer
  questionAnswered: boolean = false;
  timer: any; // To store the setInterval reference

  // Introduced variable to manage the quiz state
  quizState: 'start' | 'quiz' | 'finish' = 'start';
  isQuizFinished: Boolean = false;

  ngOnInit() {
    if (this.mode === 'ask') {
      this.quiz = new QuizDataDTO(10, "Bleach Abilities Quiz", "Standard", this.quizData);
      this.answers = Array(this.quiz.questions.length).fill(null).map(() => new AnswerDTO(this.quiz.initialTimer));
      this.quizState = 'start'; // Set state to start
      this.isQuizFinished = false; // Quiz is not finished yet
    } else if (this.mode === 'view') {
      this.quiz = new QuizDataDTO(10, "Bleach Abilities Quiz", "Standard", this.quizData);
      this.answers = [
        { chosenAnswer: "The Almighty", isCorrect: false, timer: 9, timestamp: new Date("2024-10-28T12:00:01Z") }, // Convert string to Date
        { chosenAnswer: "The Deathdealing", isCorrect: true, timer: 6, timestamp: new Date("2024-10-28T12:00:02Z") },
        { chosenAnswer: "The Balance", isCorrect: false, timer: 4, timestamp: new Date("2024-10-28T12:00:03Z") },
        { chosenAnswer: "The Compulsory", isCorrect: true, timer: 7, timestamp: new Date("2024-10-28T12:00:04Z") },
        { chosenAnswer: "The Explode", isCorrect: false, timer: 3, timestamp: new Date("2024-10-28T12:00:05Z") },
        { chosenAnswer: "The Fear", isCorrect: true, timer: 5, timestamp: new Date("2024-10-28T12:00:06Z") },
        { chosenAnswer: "The Almighty", isCorrect: false, timer: 8, timestamp: new Date("2024-10-28T12:00:07Z") },
        { chosenAnswer: "The Visionary", isCorrect: true, timer: 2, timestamp: new Date("2024-10-28T12:00:08Z") },
        { chosenAnswer: "The Heat", isCorrect: false, timer: 1, timestamp: new Date("2024-10-28T12:00:09Z") },
        { chosenAnswer: "The Miracle", isCorrect: true, timer: 9, timestamp: new Date("2024-10-28T12:00:10Z") }
      ];
       // Replace with actual API call
      this.quizState = 'quiz'; // Set state to quiz
      this.isQuizFinished = true; // Quiz is finished
    }

    
  }


  startQuiz() {
    this.currentQuestionIndex = -1;
    this.quizState = 'quiz'; // Switch to quiz state
    this.displayNextQuestion();
  }

  displayNextQuestion() {
    if (this.currentQuestionIndex < this.quiz.questions.length - 1) {
      this.startTimer();
      this.selectedAnswer = "";
      this.currentQuestionIndex++
      this.questionAnswered = false;
    } else {
      this.quizState = 'finish';
      this.isQuizFinished = true;
      clearInterval(this.timer);
      this.calculateScore();
    }
  }

  get currentQuestion() {
    return this.quizData[this.currentQuestionIndex];
  }

  selectAnswer(selectedOption: string) {
    if (this.questionAnswered) return;
    this.questionAnswered = true;
    // Store the selected answer in the current AnswerDTO instance
    this.answers[this.currentQuestionIndex].chosenAnswer = selectedOption;
    this.answers[this.currentQuestionIndex].isCorrect = selectedOption === this.quizData[this.currentQuestionIndex].correctAnswer;

    // Clear any existing timer before proceeding
    clearInterval(this.timer);
}

  nextQuestion() {
    if (this.selectedAnswer !== null) {
      this.currentQuestionIndex++;
      this.displayNextQuestion();
    }
  }

  startTimer() {
    clearInterval(this.timer); // Clear any existing timer to avoid multiple intervals
    
    // Start a new interval
    this.timer = setInterval(() => {
      this.answers[this.currentQuestionIndex].timer--;

        if (this.answers[this.currentQuestionIndex].timer <= 0) {
            clearInterval(this.timer);
            this.questionAnswered = true;
            this.answers[this.currentQuestionIndex].chosenAnswer = ""; // Mark as unanswered
            this.answers[this.currentQuestionIndex].isCorrect = false; // Mark as incorrect if time runs out
        }
    }, 1000);
}

get totalScore() {
  // Calculate total score based on questions
  return this.quiz.questions.reduce((total, question) => total + question.points, 0);
}

calculateScore() {
  // Calculate the score based on the chosen answers
  return this.answers.reduce((acc, answer, index) => {
      return acc + (answer.isCorrect ? this.quiz.questions[index].points : 0);
  }, 0);
}

getScoreClass() {
  const score = this.calculateScore();
  const percentage = (score / this.totalScore) * 100;

  if (percentage < 40) {
    return 'score-red'; // Red for scores under 40%
  } else if (percentage < 60) {
    return 'score-orange'; // Orange for scores under 60%
  } else {
    return 'score-green'; // Green for scores 60% and above
  }
} 

showResult(index: number) {
  if(!this.isQuizFinished) return
  if (index >= 0 && index < this.quiz.questions.length) {
    this.currentQuestionIndex = index;
  }
}

isCorrect(option: string): boolean {
  return option === this.quiz.questions[this.currentQuestionIndex].correctAnswer;
}

seeResults() {
  this.quizState = 'finish'; // Change state back to quiz
}

checkAnswers(){
  this.currentQuestionIndex = 0; // Reset to the first question
  this.quizState = 'quiz'; // Change state back to quiz
}
}
