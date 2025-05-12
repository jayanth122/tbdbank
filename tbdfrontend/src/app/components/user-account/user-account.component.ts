import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../data.service";
import {QrRequest} from "../../dto/QrRequest";
import {InteracValidateRequest} from "../../dto/InteracValidateRequest";
import { HeaderComponent } from "../header/header.component";

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.scss']
})
export class UserAccountComponent implements OnInit {
  firstName !: string;
  lastName !: string;
  greeting: string = '';
  accountBalance!:number;
  homeUrlPattern = /^\/#([a-zA-Z]*)$/;
  constructor(private router: Router, private dataService: DataService) {
    this.firstName = ''
    this.lastName = ''
  }

  ngOnInit() {
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid && !this.homeUrlPattern.test(this.router.url)) {
      this.router.navigate(['login'])
    }
      this.setFirstName(this.dataService.getFirstName())
      this.setLastName(this.dataService.getLastName())
      this.setAccountBalance()

}
  setFirstName(name:string) {
    if(name) {
      this.firstName = name.replace(/"/g, '');
    }
  }

  setLastName(name:string) {
    if(name) {
      this.lastName = name.replace(/"/g, '');
    }
  }
  setAccountBalance(){
    this.accountBalance = this.dataService.getAccountBalance();
  }

  goToTransactions() {
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    } else {
      this.router.navigate(['transaction'])
    }
  }
  goToInterac()
  {
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    } else {
      this.router.navigate(['interac'])
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
        this.generateQr();
      } else {
        alert("Interac Not Registered")
        let newSessionId = data.sessionId
        this.dataService.updateSession(true, newSessionId)
        this.router.navigate(['interac-register'])
      }
    })
  }

  generateQr() {
    if(!localStorage.getItem('sessionId') && !this.dataService.isLoginValid) {
      this.router.navigate(['login'])
    }
    else{
      let qrRequest = {} as QrRequest;
        qrRequest.sessionId = localStorage.getItem('sessionId') as string;
        this.dataService.generateQr(qrRequest).subscribe(data => {
          if (data.success) {
            alert(data.message)
            let newSessionId = data.sessionId
            this.dataService.setPaymentQrImage(data.qrImage);
            this.dataService.setPaymentQrPdf(data.qrPdf)
            this.dataService.updateSession(true, newSessionId);
            this.router.navigate(['qr'])
          } else {
            alert(data.message)
          }
        })

    }
  }
}

