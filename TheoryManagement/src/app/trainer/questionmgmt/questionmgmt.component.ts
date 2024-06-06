import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { AppComponent } from '../../app.component';

@Component({
  selector: 'app-questionmgmt',
  templateUrl: './questionmgmt.component.html',
  styleUrl: './questionmgmt.component.css'
})
export class QuestionmgmtComponent {
     constructor (public http:HttpClient,public app:AppComponent){}

     questions:any[]=[];

     load(){
      let url=this.app.baserUrl+'giveQuestions';
      this.http.get(url).subscribe((data:any)=>
      {
          this.questions=data;
      });
     }

  name:string='';
  add(){
    let url=this.app.baserUrl+'addQuestion';
    this.http.post(url,this.name).subscribe((data:any)=>
    {
        this.questions.push(data);
    });
  }

  
  

}
