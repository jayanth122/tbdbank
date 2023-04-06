import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../data.service";
import {InteracValidateRequest} from "../../dto/InteracValidateRequest";
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


  validateCustomerInterac() {
    let interacValidateRequest = {} as InteracValidateRequest;
    if (!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
    let sessionId = localStorage.getItem('sessionId') as string
    interacValidateRequest.sessionId = sessionId
    interacValidateRequest.email = ""
    this.dataService.validateInterac(interacValidateRequest).subscribe(data => {
      if (data.valid) {
        let newSessionId = data.sessionId
        this.dataService.updateSession(true, newSessionId)
        this.router.navigate(['interac'])
      } else {
        alert("Interac not registered")
        let newSessionId = data.sessionId
        this.dataService.updateSession(true, newSessionId)
        this.router.navigate(['interac-register'])
      }
    })
  }
}
