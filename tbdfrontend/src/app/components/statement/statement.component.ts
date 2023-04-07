import {Component, OnInit} from '@angular/core';
import {DataService} from "../../data.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {StatementRequest} from "../../dto/StatementRequest";
import {Transaction} from "../../dto/Transaction";
import {Router} from "@angular/router";
import {saveAs} from "file-saver";

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
    let statementRequest = {} as StatementRequest;
    if (!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
    statementRequest.fromDate = this.statementForm.value['fromDate']
    statementRequest.toDate = this.statementForm.value['toDate']
    statementRequest.sessionId = localStorage.getItem('sessionId') as string
    this.dataService.getTransactionStatement(statementRequest).subscribe(data => {
          if(data.success){
            let newSessionId = data.sessionId
            this.dataService.setStatementPdf(data.statementPdf)
            this.dataService.updateSession(true, newSessionId);
            this.transactions = data.transactionList
            this.submitted=false
          }
          else{
            alert(data.message)
          }
        })
  }
  downloadPdf(){
    const byteCharacters = atob(this.dataService.getStatementPdf());
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: 'application/pdf' });
    const filename = 'your_statement_filename.pdf';
    saveAs(blob, filename);
  }
}
