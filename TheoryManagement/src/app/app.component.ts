import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'TheoryManagement';
  isLoggedIn:number=0; //0no 1-student 2-trainer
  userid:number=0;
  baserUrl='http://3.86.103.140:8080/RR/';
}
