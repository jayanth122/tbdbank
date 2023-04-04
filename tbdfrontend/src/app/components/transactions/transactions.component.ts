import {Component, OnInit} from '@angular/core';
import {DataService} from "../../data.service";
import {InteracValidateRequest} from "../../dto/InteracValidateRequest";
import {Router} from "@angular/router";

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.scss']
})
export class TransactionsComponent implements OnInit{
  ngOnInit() {
  }
  constructor(private router: Router, private dataService: DataService) {
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
  }
  validateCustomerInterac(){
    const user = localStorage.getItem("userName");
    let interacValidateRequest = {} as InteracValidateRequest;
    if (user) {
      let sessionId = this.dataService.getSessionValues(user)
      interacValidateRequest.sessionId = sessionId
      interacValidateRequest.email = ""
      this.dataService.validateInterac(interacValidateRequest).subscribe(data => {
        if (data.valid) {
          alert(data.message)
          let newSessionId = data.sessionId
          this.dataService.setSessionValues(user, newSessionId)
          this.router.navigate(['interac'])
        } else {
          alert(data.message)
          let newSessionId = data.sessionId
          this.dataService.setSessionValues(user, newSessionId)
          // this.router.navigate(['interac'])
          // should create a new componenet for interacRegister.
        }
      })
    }
  }
}
