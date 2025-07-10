import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component'; // Adjust as necessary
import { InsideComponent } from './inside/inside.component'; // Adjust as necessary
import { HomeComponent } from './home/home.component'; // Adjust as necessary
import { ReportCardComponent } from './report-card/report-card.component';
import { TemplateComponent } from './template/template.component'; // Adjust as necessary
import { ClassroomDisplayComponent } from './classroom-display/classroom-display.component';
import { AssignementsComponent } from './assignements/assignements.component';
import { ChallengesGalleryComponent } from './challenges-gallery/challenges-gallery.component';
import { QuizType1Component } from './quiz-type1/quiz-type1.component';
import { ChallengeCreatorComponent } from './challenge-creator/challenge-creator.component';
import { NewReportCardComponent } from './new-report-card/new-report-card.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'app-quiz-type1', component: QuizType1Component },
  {
    path: 'inside', component: InsideComponent, children: [
      { path: 'home', component: HomeComponent },
      { path: 'report-card/:id', component: NewReportCardComponent },
      { path: 'challenges', component: ChallengesGalleryComponent },
      { path: 'template', component: TemplateComponent },
      { path: 'classroom', component: ClassroomDisplayComponent },
      { path: 'assignements', component: AssignementsComponent },
      { path: 'challenge-creator/:id', component: ChallengeCreatorComponent },
      { path: '', redirectTo: 'home', pathMatch: 'full' }, // Default route
    ]
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
