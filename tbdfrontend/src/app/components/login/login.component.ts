import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from '../../data.service';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { Buffer } from 'buffer/';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm !: FormGroup;
  submitted = false;
  constructor(public router: Router, private dataService: DataService, private formBuilder:FormBuilder) {
  }
  ngOnInit(): void {
    if(localStorage.getItem('sessionId') || this.dataService.isLoginValid) {
      this.router.navigate(['user-account'])
    }
    this.loginForm = this.formBuilder.group({
      userName:["",Validators.required],
      password:["",[Validators.required,Validators.minLength(8)]]
    })
  }
  get lfc(){
    return this.loginForm.controls
  }
  onSubmit() :void{
    this.submitted=true;
    if(this.loginForm.invalid){
      return;
    }
    this.dataService.sendLoginDetails(this.loginForm.value).subscribe(data => {
        if (data.success) {
          alert("Welcome " + data.firstName + " " + data.lastName)
          localStorage.setItem("userName",this.loginForm.value['userName'])
          this.dataService.setSessionValues(this.loginForm.value['userName'],data.uniqueSessionId)
          localStorage.setItem("firstName",JSON.stringify(data.firstName))
          localStorage.setItem("lastName",JSON.stringify(data.firstName))
          this.dataService.setIsLoginValid(true, data.uniqueSessionId);
          localStorage.setItem("loginValidity","true");
          let decoded: string;
          decoded = Buffer.from(data.encodedAccess, 'base64').toString();
          if(decoded==="CUSTOMER") {
            localStorage.setItem('username', this.loginForm.controls.userName.value);
            this.router.navigate(['user-account'])
          }
          else if(decoded=="MANAGER") {
            //
          }
        } else {
          alert(data.message)
        }
      }, error => (
        console.error(error)
      )
    )
  }
  goToReg() {
    this.router.navigate(['registration'])
  }
}
