import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from '../../data.service';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { Buffer } from 'buffer/';
import { OnInit } from '@angular/core';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm !: FormGroup;
  submitted = false;
  homeUrlPattern = /^\/#?([a-zA-Z]*)$/;
  constructor(public router: Router, public dataService: DataService, private formBuilder:FormBuilder) {
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
onSubmit(){
    this.submitted=true;
    if(this.loginForm.invalid){
      return;
    }
    console.log(JSON.stringify(this.loginForm.value));
    this.dataService.sendLoginDetails(this.loginForm.value).subscribe((data : any) => {
        if (data.success) {
          alert("Welcome " + data.firstName + " " + data.lastName)
          localStorage.setItem("userName",this.loginForm.value['userName'])
          this.dataService.setSessionValues(this.loginForm.value['userName'],data.uniqueSessionId)
          localStorage.setItem("firstName",JSON.stringify(data.firstName))
          localStorage.setItem("lastName",JSON.stringify(data.lastName))
          this.dataService.setIsLoginValid(true, data.uniqueSessionId);
          let decoded: string;
          decoded = Buffer.from(data.encodedAccess, 'base64').toString();
          if(decoded==="CUSTOMER") {
            localStorage.setItem('username', this.loginForm.controls.userName.value);
            this.dataService.fetchCustomerDetails()
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
