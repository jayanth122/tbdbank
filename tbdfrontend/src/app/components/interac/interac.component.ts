import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {DataService} from "../../data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-interac',
  templateUrl: './interac.component.html',
  styleUrls: ['./interac.component.scss']
})
export class InteracComponent implements OnInit {
  interacForm!: FormGroup;
  submitted = false;
  constructor(private formBuilder: FormBuilder, private dataService: DataService, private http: HttpClient, private router: Router) {
    console.log("Interac isValid: ", dataService.isLoginValid )
    console.log(localStorage.getItem('sessionId'));
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
  }

  ngOnInit(): void {
    this.interacForm = this.formBuilder.group({
      receiverEmail: ['', Validators.required],
      amount: ['', Validators.required],
      message: [''],
      securityQuestion: ['', Validators.required],
      securityAnswer: ['', Validators.required]
    });

  }

  onSubmit() {
    this.submitted = true;
    if (this.interacForm.invalid) {
      return;
    }
    let user = localStorage.getItem("userName");
    if (user) {
      this.interacForm.value["sessionId"] = this.dataService.getSessionValues(user)
      this.dataService.sendInteracDetails(this.interacForm.value).subscribe(data => {
        if (data.success) {
          var newSessionId = data.sessionId
          console.log(data.message)
          let user = localStorage.getItem("userName");
          if (user) {
            this.dataService.setSessionValues(user,newSessionId)
            this.dataService.updateSession(true, newSessionId);
          }
        }
      })

    }
  }
}







