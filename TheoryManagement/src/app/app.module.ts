import { NgModule } from '@angular/core';
import { BrowserModule,provideClientHydration } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { DashboardComponent } from './dashboard/dashboard.component';
import { StudentComponent } from './student/student.component';
import { TrainerComponent } from './trainer/trainer.component';
import { AttemptestsComponent } from './student/attemptests/attemptests.component';
import { ShowtestsComponent } from './student/showtests/showtests.component';
import { AnswerverificationComponent } from './trainer/answerverification/answerverification.component';
import { QuestionmgmtComponent } from './trainer/questionmgmt/questionmgmt.component';
import { TestmgmtComponent } from './trainer/testmgmt/testmgmt.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    StudentComponent,
    TrainerComponent,
    AttemptestsComponent,
    ShowtestsComponent,
    AnswerverificationComponent,
    QuestionmgmtComponent,
    TestmgmtComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
    
  
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
