import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {DataService} from "../../data.service";

@Component({
  selector: 'app-interac-register',
  templateUrl: './interac-register.component.html',
  styleUrls: ['./interac-register.component.scss']
})
export class InteracRegisterComponent implements OnInit{
  interacRegisterForm !: FormGroup
  submitted = false;
  constructor(private router : Router, private dataService : DataService, private formBuilder : FormBuilder){
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
  }
  ngOnInit() {
    this.interacRegisterForm = this.formBuilder.group({
      email: ['', Validators.required]
    });
  }
  onSubmit(){
    this.submitted=true;
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
    this.interacRegisterForm.value['sessionId'] = localStorage.getItem('sessionId')
    this.dataService.interacRegister(this.interacRegisterForm.value).subscribe(data => {
      if(data.success){
        alert(data.message)
        this.dataService.setIsLoginValid(true, data.sessionId);
        this.router.navigate(['user-account'])
      }
      else{
        alert(data.message)
      }
    },error => {
      console.error(error)
    })

  }

}
