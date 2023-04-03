import {Component, OnInit} from '@angular/core';
import {DataService} from "../../data.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {StatementRequest} from "../../dto/StatementRequest";
import {Transaction} from "../../dto/Transaction";
import {Router} from "@angular/router";

@Component({
  selector: 'app-statement',
  templateUrl: './statement.component.html',
  styleUrls: ['./statement.component.scss']
})
export class StatementComponent implements OnInit{
  public statementForm!:FormGroup
  public transactions !: Transaction[];
  submitted : boolean = false;
ngOnInit() {
  this.statementForm = this.formBuilder.group({
    fromDate:["",Validators.required],
    toDate:["",Validators.required]
  })
}
constructor(private dataService:DataService, private formBuilder:FormBuilder, private router: Router) {
  if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
    this.router.navigate(['login'])
  }
}
  public onSubmit(){
    this.submitted = true;
    const user = localStorage.getItem("userName");
    let statementRequest = {} as StatementRequest;
    if (user){
      statementRequest.fromDate = this.statementForm.value['fromDate']
      statementRequest.toDate = this.statementForm.value['toDate']
      let sessionId = this.dataService.getSessionValues(user)
      statementRequest.sessionId = sessionId
      this.dataService.getTransactionStatement(statementRequest).subscribe(data => {
          if(data.success){
            let newSessionId = data.sessionId
            this.dataService.setSessionValues(user,newSessionId);
            this.dataService.setIsLoginValid(true, newSessionId);
            this.submitted=false
          }
          else{
            alert(data.message)
          }
        }
      )
    }
  }
}
