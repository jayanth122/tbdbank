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
  constructor(private router: Router, private dataService: DataService, private formBuilder:FormBuilder) { }
  ngOnInit(): void {
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
    this.dataService.sendLoginDetails(this.loginForm.value).subscribe(data => {
        if (data.success) {
          alert("Welcome " + data.firstName + " " + data.lastName)
          let decoded: string;
          decoded = Buffer.from(data.encodedAccess, 'base64').toString();
          if(decoded==="CUSTOMER") {
            this.router.navigate(['user-account'])
          }
          else if(decoded=="MANAGER") {
            //
          }
        } else {
          alert("Login UnSuccessful")
        }
      }, error => (
        alert("Please enter valid username and password")
      )
    )
  }
  goToReg() {
    this.router.navigate(['registration'])
  }
}
