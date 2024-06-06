import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { AppComponent } from '../app.component';
import { withModule } from '@angular/core/testing';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
 constructor(public http:HttpClient,public app:AppComponent){
  }
username:string='';
password:string='';
login(){
  let url=this.app.baserUrl+'login'+this.username
  this.http.post(url,this.password).subscribe((data:any)=>
  {
 if (data==null)
 {
  window.alert('something is wrong');
 }
else if(data[0]==1){
  window.alert('wrong username');
}
else if(data[0]==2){
  window.alert('multiple accounts');

}
else if(data[0]==3){
  window.alert('wrong password');
}
else if(data[0]==0){
  this.app.userid=data[1];
  this.app.isLoggedIn=data[2];
}
else{
  window.alert('something is wrong');
}

  })
}

}

