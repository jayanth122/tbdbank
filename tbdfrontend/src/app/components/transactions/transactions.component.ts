import {Component, OnInit} from '@angular/core';
import {Transaction} from "../../dto/Transaction";
import {DataService} from "../../data.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

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
    let user = localStorage.getItem("userName");
    if (user){
      this.transactionForm.value["sessionId"] = this.dataService.getSessionValues(user)
      console.log(this.transactionForm.value)
      this.dataService.getTransactionStatement(this.transactionForm.value).subscribe(data => {
        if(data.isSuccess){
          let newSessionId = data.sessionId
          console.log(data.message)
          if(user){
            this.dataService.setSessionValues(newSessionId,user)
          }
        }
        }
      )
    }
    else{

    }
  }
}
