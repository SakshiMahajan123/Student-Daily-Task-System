import { Component } from '@angular/core';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrl: './student.component.css'
})
export class StudentComponent {

  whatToShow:number=1;// 1-showtests 2-AttemptTest

  logs:any;
  student: any;

  constructor(public app:AppComponent){
  }

  logout(){
    this.app.isLoggedIn=0
  }
}