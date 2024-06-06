import { Component } from '@angular/core';
import { StudentComponent } from '../student.component';
import { HttpClient } from '@angular/common/http';
import { AppComponent } from '../../app.component';

@Component({
  selector: 'app-attemptests',
  templateUrl: './attemptests.component.html',
  styleUrl: './attemptests.component.css'
})
export class AttemptestsComponent {

  constructor(public student:StudentComponent,public http:HttpClient,public app:AppComponent)
  {}

 showIndex:number=-1;
  load(i:number)
  {
    this.showIndex=i;

  }
  update(){
    let url=this.app.baserUrl+'update'+this.student.logs[this.showIndex].id;
    this.http.post(url,this.student.logs[this.showIndex].answer).subscribe((data:any)=>
    {
      if(data==null||data==0){
        window.alert('something is wrong');
      }
      else{
        window.alert('Done');
      }
    })
  }
  submit(){
    let url=this.app.baserUrl+'submit'+this.student.logs[this.showIndex].id;
    this.http.get(url).subscribe((data:any)=>
    {
  if(data==null||data==0){
    window.alert('Something is wrong');
  }
  else{
    this.student.logs[this.showIndex].submit=1;
    window.alert('Done');
  }
    })
  }

  GoBackTestMenu(){
    let url=this.app.baserUrl+'goback';
    this.http.get(url).subscribe((data:any)=>
    {

    })
  }

  GoBack()
{
  
  this.student.whatToShow = 1;
}

}
