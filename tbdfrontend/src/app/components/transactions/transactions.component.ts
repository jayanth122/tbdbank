import {Component, OnInit} from '@angular/core';
import {Transaction} from "../../dto/Transaction";
import {DataService} from "../../data.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {StatementRequest} from "../../dto/StatementRequest";


@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.scss']
})
export class TransactionsComponent implements OnInit{
  public transactions!:Transaction[];
  transactionForm !: FormGroup
  submitted = false
  ngOnInit() {
    this.transactionForm = this.formBuilder.group({
      fromDate:["",Validators.required],
      toDate:["",Validators.required]
    })
  }

  constructor(private dataService:DataService, private formBuilder:FormBuilder) {
  }
  public onSubmit(){
    this.submitted = true;
    const user = localStorage.getItem("userName");
    let statementRequest = {} as StatementRequest;
    if (user){
      statementRequest.fromDate = this.transactionForm.value['fromDate']
      statementRequest.toDate = this.transactionForm.value['toDate']
      let sessionId = this.dataService.getSessionValues(user)
      statementRequest.sessionId = sessionId
      this.dataService.getTransactionStatement(statementRequest).subscribe(data => {
        if(data.success){
          let newSessionId = data.sessionId
          this.dataService.setSessionValues(user,newSessionId)
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
