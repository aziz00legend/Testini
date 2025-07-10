import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// PrimeNG Modules
import { CalendarModule } from 'primeng/calendar';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { TableModule } from 'primeng/table';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { DialogModule } from 'primeng/dialog';
import { StepperModule } from 'primeng/stepper';
import { TabViewModule } from 'primeng/tabview';
import { DropdownModule } from 'primeng/dropdown';
import { RadioButtonModule } from 'primeng/radiobutton';
import { CheckboxModule } from 'primeng/checkbox';
import { ImageModule } from 'primeng/image';
import { ToolbarModule } from 'primeng/toolbar';
import { DividerModule } from 'primeng/divider';
import { SelectButtonModule } from 'primeng/selectbutton';
import { SplitterModule } from 'primeng/splitter';
import { DragDropModule } from 'primeng/dragdrop';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessagesModule } from 'primeng/messages';
import { TreeTableModule } from 'primeng/treetable';
import { TreeModule } from 'primeng/tree';
import { SessionTreeComponent } from './session-tree/session-tree.component';
import { InputTextModule } from 'primeng/inputtext';
import { SplitButtonModule } from 'primeng/splitbutton';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';

// PrimeNG Services
import { MessageService, ConfirmationService } from 'primeng/api';

// Custom Components
import { NavbarComponent } from './navbar/navbar.component';
import { ReportCardComponent } from './report-card/report-card.component';
import { HomeComponent } from './home/home.component';
import { TemplateComponent } from './template/template.component';
import { TemplateCardComponent } from './template-card/template-card.component';
import { LoginComponent } from './login/login.component';
import { InsideComponent } from './inside/inside.component';
import { ClassroomDisplayComponent } from './classroom-display/classroom-display.component';
import { ClassroomCardComponent } from './classroom-card/classroom-card.component';
import { AssignementsComponent } from './assignements/assignements.component';
import { ChallengesGalleryComponent } from './challenges-gallery/challenges-gallery.component';
import { QuizType1Component } from './quiz-type1/quiz-type1.component';
import { ChallengeCreatorComponent } from './challenge-creator/challenge-creator.component';
import { Quiz1Component } from './questions/quiz1/quiz1.component';
import { CustomStepperComponent } from './custom-stepper/custom-stepper.component';
import { QRQuestionComponent } from './questions/qr-question/qr-question.component';

import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { NewReportCardComponent } from './new-report-card/new-report-card.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ReportCardComponent,
    HomeComponent,
    TemplateComponent,
    TemplateCardComponent,
    LoginComponent,
    InsideComponent,
    ClassroomDisplayComponent,
    ClassroomCardComponent,
    AssignementsComponent,
    ChallengesGalleryComponent,
    QuizType1Component,
    ChallengeCreatorComponent,
    Quiz1Component,
    CustomStepperComponent,
    QRQuestionComponent,
    SessionTreeComponent,
    NewReportCardComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    // PrimeNG Modules
    CalendarModule,
    AutoCompleteModule,
    ButtonModule,
    TagModule,
    CascadeSelectModule,
    TableModule,
    BreadcrumbModule,
    DialogModule,
    StepperModule,
    TabViewModule,
    DropdownModule,
    RadioButtonModule,
    CheckboxModule,
    ImageModule,
    ToolbarModule,
    DividerModule,

    SelectButtonModule, TagModule,

    SplitterModule, DragDropModule, ToastModule, ConfirmDialogModule, TreeTableModule, TreeModule, IconFieldModule, InputIconModule, MessagesModule

    , DragDropModule,

    ToastModule,
    ConfirmDialogModule,
    MessagesModule,

    TreeTableModule,
    TreeModule, ProgressSpinnerModule,
    InputTextModule,
    SplitButtonModule,
    IconFieldModule,
    InputIconModule

  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withFetch()),
    MessageService,
    ConfirmationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
